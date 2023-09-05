/*** Licensed under Apache-2.0 2022-2023 ***/
package com.rxdata.catalogservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "catalog-service", version = "v1"),
        servers = @Server(url = "/${spring.application.name}"))
public class SwaggerConfig {}
