/* Licensed under Apache-2.0 2022-2023 */
package com.example.api.gateway.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.webflux.core.configuration.MultipleOpenApiSupportConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration(proxyBeanMethods = false)
@OpenAPIDefinition(
        info =
                @Info(
                        title = "api-gateway",
                        description = "Documentation for all the Microservices in Demo Application",
                        version = "v1",
                        contact =
                                @Contact(
                                        name = "Raja Kolli",
                                        url = "https://github.com/rajadilipkolli")),
        servers = @Server(url = "/"))
@AutoConfigureBefore(MultipleOpenApiSupportConfiguration.class)
public class SwaggerConfig {

    @Bean
    @Lazy(value = false)
    public List<GroupedOpenApi> groupedOpenApis(
            RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls =
                swaggerUiConfigProperties.getUrls();
        return locator.getRouteDefinitions()
                .toStream()
                .map(RouteDefinition::getId)
                .filter(id -> id.endsWith("-service"))
                .map(
                        routeDefinitionId -> {
                            String name = extractNameFromRouteDefinitionId(routeDefinitionId);

                            swaggerUrls.add(
                                    new AbstractSwaggerUiConfigProperties.SwaggerUrl(
                                            name,
                                            "/" + routeDefinitionId + "/v3/api-docs",
                                            routeDefinitionId));
                            return GroupedOpenApi.builder()
                                    .pathsToMatch("/" + name + "/**")
                                    .group(name)
                                    .build();
                        })
                .collect(Collectors.toList());
    }

    private String extractNameFromRouteDefinitionId(String routeDefinitionId) {
        return routeDefinitionId.replaceAll("-service", "");
    }
}
