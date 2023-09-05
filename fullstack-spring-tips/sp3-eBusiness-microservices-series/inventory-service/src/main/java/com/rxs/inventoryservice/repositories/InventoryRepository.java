/* Licensed under Apache-2.0 2021-2022 */
package com.rxs.inventoryservice.repositories;

import com.rxs.inventoryservice.entities.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductCode(String productCode);

    List<Inventory> findByProductCodeIn(List<String> productCodes);
}
