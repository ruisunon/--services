/* Licensed under Apache-2.0 2021-2022 */
package com.rxs.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes().route(routeSpec -> routeSpec.path("/get")
                      .filters(filterSpec -> filterSpec.addRequestHeader("MyHeader", "MyURI")
                      .addRequestParameter("Param", "MyValue")).uri("http://httpbin.org:80"))
                      .route(p -> p.path("/ORDER-SERVICE-new/**")
                      .filters(f -> f.rewritePath("/ORDER-SERVICE-new/(?<segment>.*)","/ORDER-SERVICE-feign/${segment}"))
                      .uri("lb://ORDER-SERVICE")).build();
    }
}
