package com.microservices.products.Services;

import com.microservices.products.Models.Category;
import com.microservices.products.Requests.CreateCategoryRequest;
import com.microservices.products.Requests.PatchCategoryRequest;

import java.util.List;

public interface ICategoryService {
    List<Category> GetCategories();
    Category GetCategoryById(Integer id);
    int DeleteCategory(Integer id);
    Category CreateCategory(CreateCategoryRequest request);
    Category PatchCategory(PatchCategoryRequest request);
}
