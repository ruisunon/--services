/* Licensed under Apache-2.0 2022 */
package com.rxs.inventoryservice.services;

import com.rxs.common.dtos.ProductDto;
import com.rxs.inventoryservice.entities.Inventory;
import com.rxs.inventoryservice.repositories.InventoryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductManageService {

    private final InventoryRepository inventoryRepository;

    public void manage(ProductDto productDto) {
        Inventory inventory = new Inventory();
        inventory.setProductCode(productDto.code());
        inventory.setAvailableQuantity(0);
        this.inventoryRepository.save(inventory);
    }
}
