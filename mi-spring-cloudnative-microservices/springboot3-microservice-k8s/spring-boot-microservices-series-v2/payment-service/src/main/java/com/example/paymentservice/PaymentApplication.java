/* Licensed under Apache-2.0 2021-2022 */
package com.example.paymentservice;

import com.example.paymentservice.config.ApplicationProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class PaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }
}
