/* Licensed under Apache-2.0 2021-2022 */
package com.example.inventoryservice.config;

import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.repositories.InventoryRepository;
import com.example.inventoryservice.utils.AppConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile(AppConstants.PROFILE_LOCAL)
public class Initializer implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) {
        log.info("Running Initializer.....");
        if (this.inventoryRepository.count() == 0) {
            Random r = new Random();
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
