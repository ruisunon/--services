/*** Licensed under Apache-2.0 2021-2023 ***/
package com.rxdata.catalogservice.repositories;

import com.rxdata.catalogservice.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    long countDistinctByCodeAllIgnoreCaseIn(List<String> code);

    Optional<Product> findByCodeAllIgnoreCase(String code);
}
