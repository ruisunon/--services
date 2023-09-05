package com.rytest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name="taskExecutor")
     public Executor taskExecutor(){
         ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
         executor.setCorePoolSize(2);
         executor.setMaxPoolSize(2);
         executor.setQueueCapacity(100);
         executor.setThreadGroupName("async");
         executor.setThreadNamePrefix("async-");
         executor.initialize();
         return executor;
     }

    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
