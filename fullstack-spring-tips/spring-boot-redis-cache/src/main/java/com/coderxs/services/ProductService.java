package com.coderxs.services;

import com.coderxs.models.Customer;
import com.coderxs.models.Product;

import java.util.List;

public interface ProductService {

    public Product getProductById(long id);

    public List<Product> getAll();

    public Product add(Product product);

    public Product update(Product product);

    public void delete(long id);
}
