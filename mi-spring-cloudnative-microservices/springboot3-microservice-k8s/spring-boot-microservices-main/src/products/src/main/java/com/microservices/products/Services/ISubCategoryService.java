package com.microservices.products.Services;

import com.microservices.products.Models.Category;
import com.microservices.products.Models.SubCategory;
import com.microservices.products.Requests.CreateSubCategoryRequest;
import com.microservices.products.Requests.PatchSubCategoryRequest;

import java.util.List;

public interface ISubCategoryService {

    List<SubCategory> GetSubCategories();
    SubCategory GetSubCategoryById(Integer id);
    int DeleteSubCategory(Integer id);
    SubCategory CreateSubCategory(CreateSubCategoryRequest request);
    SubCategory PatchSubCategory(PatchSubCategoryRequest request);
}
