/* Licensed under Apache-2.0 2022 */
package com.example.orderservice.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

// @HttpExchange("lb://catalog-service/")
// @HttpExchange("http://localhost:18080/catalog-service")
// @HttpExchange(url = "${application.catalog-service-url}")
public interface CatalogServiceProxy {

    @GetExchange("/api/catalog/exists/{productIds}")
    Boolean productsExists(@PathVariable List<String> productIds);
}
