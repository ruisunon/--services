/* Licensed under Apache-2.0 2021-2022 */
package com.rxs.apigateway.web.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
@Slf4j
@RequiredArgsConstructor
public class RestExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof WebExchangeBindException webExchangeBindException) {

            log.debug("errors:" + webExchangeBindException.getFieldErrors());
            var errors = new ApplicationErrors("validation_failure", "Validation failed.");
            webExchangeBindException.getFieldErrors()
                    .forEach(e -> errors.add(e.getField(), e.getCode(), e.getDefaultMessage()));

            log.debug("handled errors::" + errors);
            try {
                exchange.getResponse().setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                var db =  new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(errors));

                // write the given data buffer to the response
                // and return a Mono that signals when it's done
                return exchange.getResponse().writeWith(Mono.just(db));

            } catch (JsonProcessingException e) {
                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                return exchange.getResponse().setComplete();
            }
        }
        return Mono.error(ex);
    }
}
