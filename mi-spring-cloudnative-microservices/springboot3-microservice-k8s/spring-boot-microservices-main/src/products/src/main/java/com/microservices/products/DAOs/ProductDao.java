package com.microservices.products.DAOs;

import com.microservices.products.Models.Product;
import com.microservices.products.Requests.FindProductRequest;

import java.util.List;

public interface ProductDao {
    List<Product> GetAll();
    Product FindProduct(FindProductRequest request);
    Product Patch(Product product);
    Product Save(Product prod);
    int Delete(Integer id);
}
