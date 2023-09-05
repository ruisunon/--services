package org.testcontainers.dockerclient;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.RemoteApiVersion;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.transport.NamedPipeSocket;
import com.github.dockerjava.transport.SSLConfig;
import com.github.dockerjava.transport.UnixSocket;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Throwables;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Awaitility;
import org.jetbrains.annotations.Nullable;
import org.rnorth.ducttape.TimeoutException;
import org.rnorth.ducttape.ratelimits.RateLimiter;
import org.rnorth.ducttape.ratelimits.RateLimiterBuilder;
import org.rnorth.ducttape.unreliables.Unreliables;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.UnstableAPI;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.net.SocketFactory;

/**
 * Mechanism to find a viable Docker client configuration according to the host system environment.
 */
@Slf4j
public abstract class DockerClientProviderStrategy {

    @Getter(lazy = true)
    private final DockerClient dockerClient = getClientForConfig(getTransportConfig());

    private String dockerHostIpAddress;

    @Getter
    private Info info;

    private final RateLimiter PING_RATE_LIMITER = RateLimiterBuilder
        .newBuilder()
        .withRate(10, TimeUnit.SECONDS)
        .withConstantThroughput()
        .build();

    private static final AtomicBoolean FAIL_FAST_ALWAYS = new AtomicBoolean(false);

    /**
     * @return a short textual description of the strategy
     */
    public abstract String getDescription();

    protected boolean isApplicable() {
        return true;
    }

    protected boolean isPersistable() {
        return true;
    }

    /**
     * @return highest to lowest priority value
     */
    protected int getPriority() {
        return 0;
    }

    /**
     * @throws InvalidConfigurationException if this strategy fails
     */
    public abstract TransportConfig getTransportConfig() throws InvalidConfigurationException;

    /**
     * @return a usable, tested, Docker client configuration for the host system environment
     *
     * @deprecated use {@link #getDockerClient()}
     */
    @Deprecated
    public DockerClient getClient() {
        DockerClient dockerClient = getDockerClient();
        try {
            Unreliables.retryUntilSuccess(
                30,
                TimeUnit.SECONDS,
                () -> {
                    return PING_RATE_LIMITER.getWhenReady(() -> {
                        log.debug("Pinging docker daemon...");
                        dockerClient.pingCmd().exec();
                        log.debug("Pinged");
                        return true;
                    });
                }
            );
        } catch (TimeoutException e) {
            IOUtils.closeQuietly(dockerClient);
            throw e;
        }
        return dockerClient;
    }

    /**
     * TODO we should consider moving this to docker-java at some point
     */
    @UnstableAPI
    protected boolean test() {
        TransportConfig transportConfig = getTransportConfig();
        URI dockerHost = transportConfig.getDockerHost();

        Callable<Socket> socketProvider;
        SocketAddress socketAddress;
        switch (dockerHost.getScheme()) {
            case "tcp":
            case "http":
            case "https":
                SocketFactory socketFactory = SocketFactory.getDefault();
                SSLConfig sslConfig = transportConfig.getSslConfig();
                if (sslConfig != null) {
                    try {
                        socketFactory = sslConfig.getSSLContext().getSocketFactory();
                    } catch (
                        KeyManagementException
                        | UnrecoverableKeyException
                        | NoSuchAlgorithmException
                        | KeyStoreException e
                    ) {
                        log.warn("Exception while creating SSLSocketFactory", e);
                        return false;
                    }
                }
                socketProvider = socketFactory::createSocket;
                socketAddress = new InetSocketAddress(dockerHost.getHost(), dockerHost.getPort());
                break;
            case "unix":
            case "npipe":
                if (!new File(dockerHost.getPath()).exists()) {
                    log.debug("DOCKER_HOST socket file '{}' does not exist", dockerHost.getPath());
                    return false;
                }
                socketProvider =
                    () -> {
                        switch (dockerHost.getScheme()) {
                            case "unix":
                                return UnixSocket.get(dockerHost.getPath());
                            case "npipe":
                                return new NamedPipeSocket(dockerHost.getPath());
                            default:
                                throw new IllegalStateException("Unexpected scheme " + dockerHost.getScheme());
                        }
                    };
                socketAddress = new InetSocketAddress("localhost", 2375);
                break;
            default:
                log.warn("Unknown DOCKER_HOST scheme {}, skipping the strategy test...", dockerHost.getScheme());
                return true;
        }

        try (Socket socket = socketProvider.call()) {
            Duration timeout = Duration.ofMillis(200);
            Awaitility
                .await()
                .atMost(TestcontainersConfiguration.getInstance().getClientPingTimeout(), TimeUnit.SECONDS)
                .pollInterval(timeout)
                .pollDelay(Duration.ofSeconds(0)) // start checking immediately
                .ignoreExceptionsInstanceOf(SocketTimeoutException.class)
                .untilAsserted(() -> socket.connect(socketAddress, (int) timeout.toMillis()));
            return true;
        } catch (Exception e) {
            log.warn("DOCKER_HOST {} is not listening", dockerHost);
            return false;
        }
    }

    /**
     * Determine the right DockerClientConfig to use for building clients by trial-and-error.
     *
     * @return a working DockerClientConfig, as determined by successful execution of a ping command
     */
    public static DockerClientProviderStrategy getFirstValidStrategy(List<DockerClientProviderStrategy> strategies) {
        if (FAIL_FAST_ALWAYS.get()) {
            throw new IllegalStateException(
                "Previous attempts to find a Docker environment failed. Will not retry. Please see logs and check configuration"
            );
        }

        List<String> configurationFailures = new ArrayList<>();
        List<DockerClientProviderStrategy> allStrategies = new ArrayList<>();

        // The environment has the highest priority
        allStrategies.add(new EnvironmentAndSystemPropertyClientProviderStrategy());

        // Next strategy to try out is the one configured using the Testcontainers configuration mechanism
        loadConfiguredStrategy().ifPresent(allStrategies::add);

        // Finally, add all other strategies ordered by their internal priority
        strategies
            .stream()
            .sorted(Comparator.comparing(DockerClientProviderStrategy::getPriority).reversed())
            .collect(Collectors.toCollection(() -> allStrategies));

        Predicate<DockerClientProviderStrategy> distinctStrategyClassPredicate = new Predicate<DockerClientProviderStrategy>() {
            final Set<Class<? extends DockerClientProviderStrategy>> classes = new HashSet<>();

            @Override
            public boolean test(DockerClientProviderStrategy dockerClientProviderStrategy) {
                return classes.add(dockerClientProviderStrategy.getClass());
            }
        };

        return allStrategies
            .stream()
            .filter(distinctStrategyClassPredicate)
            .filter(DockerClientProviderStrategy::isApplicable)
            .filter(strategy -> tryOutStrategy(configurationFailures, strategy))
            .findFirst()
            .orElseThrow(() -> {
                log.error(
                    "Could not find a valid Docker environment. Please check configuration. Attempted configurations were:\n" +
                    configurationFailures.stream().map(it -> "\t" + it).collect(Collectors.joining("\n")) +
                    "As no valid configuration was found, execution cannot continue.\n" +
                    "See https://www.testcontainers.org/on_failure.html for more details."
                );

                FAIL_FAST_ALWAYS.set(true);
                return new IllegalStateException(
                    "Could not find a valid Docker environment. Please see logs and check configuration"
                );
            });
    }

    private static boolean tryOutStrategy(List<String> configurationFailures, DockerClientProviderStrategy strategy) {
        try {
            log.debug("Trying out strategy: {}", strategy.getClass().getSimpleName());

            if (!strategy.test()) {
                log.debug("strategy {} did not pass the test", strategy.getClass().getSimpleName());
                return false;
            }

            strategy.info = strategy.getDockerClient().infoCmd().exec();
            log.info("Found Docker environment with {}", strategy.getDescription());
            log.debug(
                "Transport type: '{}', Docker host: '{}'",
                TestcontainersConfiguration.getInstance().getTransportType(),
                strategy.getTransportConfig().getDockerHost()
            );

            log.debug("Checking Docker OS type for {}", strategy.getDescription());
            String osType = strategy.getInfo().getOsType();
            if (StringUtils.isBlank(osType)) {
                log.warn("Could not determine Docker OS type");
            } else if (!osType.equals("linux")) {
                log.warn("{} is currently not supported", osType);
                throw new InvalidConfigurationException(osType + " containers are currently not supported");
            }

            if (strategy.isPersistable()) {
                TestcontainersConfiguration
                    .getInstance()
                    .updateUserConfig("docker.client.strategy", strategy.getClass().getName());
            }

            return true;
        } catch (Exception | ExceptionInInitializerError | NoClassDefFoundError e) {
            @Nullable
            String throwableMessage = e.getMessage();
            @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
            Throwable rootCause = Throwables.getRootCause(e);
            @Nullable
            String rootCauseMessage = rootCause.getMessage();

            String failureDescription;
            if (throwableMessage != null && throwableMessage.equals(rootCauseMessage)) {
                failureDescription =
                    String.format(
                        "%s: failed with exception %s (%s)",
                        strategy.getClass().getSimpleName(),
                        e.getClass().getSimpleName(),
                        throwableMessage
                    );
            } else {
                failureDescription =
                    String.format(
                        "%s: failed with exception %s (%s). Root cause %s (%s)",
                        strategy.getClass().getSimpleName(),
                        e.getClass().getSimpleName(),
                        throwableMessage,
                        rootCause.getClass().getSimpleName(),
                        rootCauseMessage
                    );
            }
            configurationFailures.add(failureDescription);

            log.debug(failureDescription);
            return false;
        }
    }

    private static Optional<? extends DockerClientProviderStrategy> loadConfiguredStrategy() {
        String configuredDockerClientStrategyClassName = TestcontainersConfiguration
            .getInstance()
            .getDockerClientStrategyClassName();

        return Stream
            .of(configuredDockerClientStrategyClassName)
            .filter(Objects::nonNull)
            .flatMap(it -> {
                try {
                    Class<? extends DockerClientProviderStrategy> strategyClass = (Class) Thread
                        .currentThread()
                        .getContextClassLoader()
                        .loadClass(it);
                    return Stream.of(strategyClass.newInstance());
                } catch (ClassNotFoundException e) {
                    log.warn(
                        "Can't instantiate a strategy from {} (ClassNotFoundException). " +
                        "This probably means that cached configuration refers to a client provider " +
                        "class that is not available in this version of Testcontainers. Other " +
                        "strategies will be tried instead.",
                        it
                    );
                    return Stream.empty();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.warn("Can't instantiate a strategy from {}", it, e);
                    return Stream.empty();
                }
            })
            // Ignore persisted strategy if it's not persistable anymore
            .filter(DockerClientProviderStrategy::isPersistable)
            .peek(strategy -> {
                log.info(
                    "Loaded {} from ~/.testcontainers.properties, will try it first",
                    strategy.getClass().getName()
                );
            })
            .findFirst();
    }

    public static DockerClient getClientForConfig(TransportConfig transportConfig) {
        final DockerHttpClient dockerHttpClient;

        String transportType = TestcontainersConfiguration.getInstance().getTransportType();
        switch (transportType) {
            case "httpclient5":
                dockerHttpClient =
                    new ZerodepDockerHttpClient.Builder()
                        .dockerHost(transportConfig.getDockerHost())
                        .sslConfig(transportConfig.getSslConfig())
                        .build();
                break;
            default:
                throw new IllegalArgumentException("Unknown transport type '" + transportType + "'");
        }

        DefaultDockerClientConfig.Builder configBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder();

        if (configBuilder.build().getApiVersion() == RemoteApiVersion.UNKNOWN_VERSION) {
            configBuilder.withApiVersion(RemoteApiVersion.VERSION_1_32);
        }
        return DockerClientImpl.getInstance(
            new AuthDelegatingDockerClientConfig(
                configBuilder.withDockerHost(transportConfig.getDockerHost().toString()).build()
            ),
            new HeadersAddingDockerHttpClient(
                dockerHttpClient,
                Collections.singletonMap("x-tc-sid", DockerClientFactory.SESSION_ID)
            )
        );
    }

    public synchronized String getDockerHostIpAddress() {
        if (dockerHostIpAddress == null) {
            dockerHostIpAddress = resolveDockerHostIpAddress(getDockerClient(), getTransportConfig().getDockerHost());
        }
        return dockerHostIpAddress;
    }

    @VisibleForTesting
    static String resolveDockerHostIpAddress(DockerClient client, URI dockerHost) {
        String hostOverride = System.getenv("TESTCONTAINERS_HOST_OVERRIDE");
        if (!StringUtils.isBlank(hostOverride)) {
            return hostOverride;
        }

        switch (dockerHost.getScheme()) {
            case "http":
            case "https":
            case "tcp":
                return dockerHost.getHost();
            case "unix":
            case "npipe":
                if (DockerClientConfigUtils.IN_A_CONTAINER) {
                    return client
                        .inspectNetworkCmd()
                        .withNetworkId("bridge")
                        .exec()
                        .getIpam()
                        .getConfig()
                        .stream()
                        .filter(it -> it.getGateway() != null)
                        .findAny()
                        .map(Network.Ipam.Config::getGateway)
                        .orElseGet(() -> {
                            return DockerClientConfigUtils.getDefaultGateway().orElse("localhost");
                        });
                }
                return "localhost";
            default:
                return null;
        }
    }
}
