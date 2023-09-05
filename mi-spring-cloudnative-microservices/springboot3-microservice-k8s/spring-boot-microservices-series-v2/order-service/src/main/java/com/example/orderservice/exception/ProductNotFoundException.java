/* Licensed under Apache-2.0 2022 */
package com.example.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;
import java.util.List;

public class ProductNotFoundException extends ErrorResponseException {

    public ProductNotFoundException(List<String> productIds) {
        super(HttpStatus.NOT_FOUND, asProblemDetail(productIds), null);
    }

    private static ProblemDetail asProblemDetail(List<String> productIds) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND, "One or More products Not found from " + productIds);
        problemDetail.setTitle("Product Not Found");
        problemDetail.setType(URI.create("http://api.products.com/errors/not-found"));
        problemDetail.setProperty("errorCategory", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
