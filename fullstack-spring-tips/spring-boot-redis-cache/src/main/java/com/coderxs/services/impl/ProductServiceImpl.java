package com.coderxs.services.impl;

import com.coderxs.models.Customer;
import com.coderxs.models.Product;
import com.coderxs.repositories.CustomerRepository;
import com.coderxs.repositories.ProductRepository;
import com.coderxs.services.CustomerService;
import com.coderxs.services.ProductService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "productCache")
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Cacheable(cacheNames = "products")
    @Override
    public List<Product> getAll() {
        waitSomeTime();
        return this.productRepository.findAll();
    }

    @CacheEvict(cacheNames = "customers", allEntries = true)
    @Override
    public Product add(Product product) {
        return this.productRepository.save(product);
    }

    @CacheEvict(cacheNames = "customers", allEntries = true)
    @Override
    public Product update(Product product) {
        Optional<Product> optProduct = this.productRepository.findById(product.getId());
        if (!optProduct.isPresent())
            return null;
        Product repProduct = optProduct.get();
        repProduct.setName(product.getName());
        repProduct.setCategory(product.getCategory());
        repProduct.setPrice(product.getPrice());
        repProduct.setDescription(product.getDescription());
        repProduct.setMadeDate(product.getMadeDate());
        return this.productRepository.save(repProduct);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "customer", key = "#id"),
            @CacheEvict(cacheNames = "customers", allEntries = true) })
    @Override
    public void delete(long id) {
        this.productRepository.deleteById(id);
    }

    @Cacheable(cacheNames = "customer", key = "#id", unless = "#result == null")
    @Override
    public Product getProductById(long id) {
        waitSomeTime();
        return this.productRepository.findById(id).orElse(null);
    }

    private void waitSomeTime() {
        System.out.println("Long Wait Begin");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Long Wait End");
    }

}