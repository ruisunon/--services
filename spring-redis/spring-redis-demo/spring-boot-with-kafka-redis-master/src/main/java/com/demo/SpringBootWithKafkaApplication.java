package com.demo;

import com.demo.config.ApplicationProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

@Log4j2
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableCaching
@CacheEvict(value = "testCache", allEntries = true)
public class SpringBootWithKafkaApplication {

	public static void main(String[] args) {

		final var context = SpringApplication.run(SpringBootWithKafkaApplication.class, args);
		final var serverProps = context.getBean(ServerProperties.class);
		final var applicationProps = context.getBean(ApplicationProperties.class);
		final var port = serverProps.getPort();
		final var clientOriginUrl = applicationProps.getClientOriginUrl();
		final var audience = applicationProps.getAudience();

		if (port == null || port == 0) {
			exitWithMissingEnv(context, "PORT");
		}

		if (Objects.isNull(clientOriginUrl) || clientOriginUrl.isBlank()) {
			exitWithMissingEnv(context, "CLIENT_ORIGIN_URL");
		}

		if (Objects.isNull(audience) || audience.isEmpty()) {
			exitWithMissingEnv(context, "AUTH0_AUDIENCE");
		}
	}
	private static void exitWithMissingEnv(final ConfigurableApplicationContext context, final String env) {
		final var exitCode = SpringApplication.exit(context, () -> 1);

		log.error("[Fatal] Missing or empty environment variable: {}", env);
		System.exit(exitCode);
	}
}
