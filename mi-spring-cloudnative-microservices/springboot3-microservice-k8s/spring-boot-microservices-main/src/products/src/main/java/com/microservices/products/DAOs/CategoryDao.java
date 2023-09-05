package com.microservices.products.DAOs;

import com.microservices.products.Models.Category;
import org.springframework.stereotype.Component;

import java.util.List;

public interface CategoryDao {
    List<Category> GetAll();
    Category GetById(Integer id);
    Category Patch(Category product);
    Category Save(Category prod);
    int Delete(Integer id);
}
