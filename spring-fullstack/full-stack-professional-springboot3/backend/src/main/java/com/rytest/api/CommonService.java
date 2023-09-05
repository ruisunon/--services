package com.rytest.api;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CommonService {

    private final WebClient webClient;

    public CommonService(WebClient webClient) {}
}
