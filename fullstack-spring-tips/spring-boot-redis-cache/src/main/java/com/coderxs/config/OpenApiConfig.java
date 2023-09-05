package com.coderxs.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI(){
        Contact contact = new Contact();
        contact.setEmail("coderxs@gmail.com");
        contact.setName("coderxs");
        contact.setUrl("https://www.coderxs.com");
        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
        Info info = new Info()
                .title("Customer Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage customers.").termsOfService("https://www.coderxs.com/terms")
                .license(mitLicense);
        return new OpenAPI().info(info);
    }
}
