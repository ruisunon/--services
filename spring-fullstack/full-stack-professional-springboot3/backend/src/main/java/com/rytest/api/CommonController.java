package com.rytest.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestController
@RequestMapping("/user-details")
public class CommonController {
    private static final Log LOG = LogFactory.getLog(CommonController.class);
    private final WebClient webClient;
    public

    @GetMapping("/{id}")
    @Retryable(value= WebClientResponseException.InternalServerError.class)
    public ResponseEntity<String> getUserDetails(@PathVariable("id") String id) {
        ResponseEntity<String> response = webClient.mutate()
    }

}
