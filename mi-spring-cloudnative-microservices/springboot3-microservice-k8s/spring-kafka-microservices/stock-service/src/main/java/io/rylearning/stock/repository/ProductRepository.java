package io.rylearning.stock.repository;

import org.springframework.data.repository.CrudRepository;
import io.rylearning.stock.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
