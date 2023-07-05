package io.prots.app.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@Configuration
//@OpenAPIDefinition(
//		info = @Info(title = "multi-producers-consumers", version = "v1"),
//		servers = @Server(url = "/"))
public class SwaggerConfig {

	@Bean
	public OpenAPI expenseAPI() {
		return new OpenAPI()
				.info(new Info().title("Project tracking API")
						.description("API for Project tracking Application")
						.version("v0.0.1")
						.license(new License().name("Apache License Version 2.0").url("http://rxs.com")))
				.externalDocs(new ExternalDocumentation()
						.description("Project tracking Wiki Documentation")
						.url("https://ptapp.wiki/docs"));
	}
}
