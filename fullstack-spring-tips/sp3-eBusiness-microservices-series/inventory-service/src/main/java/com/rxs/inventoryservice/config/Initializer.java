/* Licensed under Apache-2.0 2021-2022 */
package com.rxs.inventoryservice.config;

import com.rxs.inventoryservice.entities.Inventory;
import com.rxs.inventoryservice.repositories.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Initializer implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) {
        log.info("Running Initializer.....");
        if (this.inventoryRepository.count() == 0) {
            SecureRandom r = new SecureRandom();
            List<Inventory> inventoryList = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                int count = r.nextInt(1000);
                Inventory inventory = new Inventory(null, "Product" + i, count, 0);
                inventoryList.add(inventory);
            }
            inventoryRepository.saveAll(inventoryList);
        }
    }
}
