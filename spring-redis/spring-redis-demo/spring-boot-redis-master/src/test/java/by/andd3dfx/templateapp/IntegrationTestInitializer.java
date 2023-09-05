package by.andd3dfx.templateapp;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;
import java.util.stream.Stream;

public class IntegrationTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:6.2.1-alpine"))
            .withExposedPorts(6379);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        startContainers();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        MapPropertySource propertySource = new MapPropertySource(
                "test-containers", (Map) createConnectionConfiguration()
        );
        environment.getPropertySources().addFirst(propertySource);
    }

    private void startContainers() {
        Startables.deepStart(Stream.of(redis)).join();
        // we can add further containers
        // here like rabbitmq or other databases
    }

    private static Map<String, Object> createConnectionConfiguration() {
        return Map.of(
                "REDIS_HOST", redis.getHost(),
                "REDIS_PORT", redis.getFirstMappedPort()
        );
    }
}