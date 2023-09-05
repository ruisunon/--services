/* Licensed under Apache-2.0 2021-2023 */
package com.rxdata.paymentservice.config;

import com.rxdata.paymentservice.entities.Customer;
import com.rxdata.paymentservice.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.datafaker.Faker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
@Slf4j
public class Initializer implements CommandLineRunner {

    private final CustomerRepository repository;

    @Override
    public void run(String... args) {
        log.info("Running Initializer.....");
        SecureRandom r = new SecureRandom();
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            int count = r.nextInt(1000);
            Customer c =new Customer(null, faker.name().fullName(),faker.name().lastName() + "@gmail.com",
                            faker.address().fullAddress(), count,0);
            repository.save(c);
        }
    }
}
