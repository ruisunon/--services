package com.microservices.products.Requests;

public record CreateSubCategoryRequest(Integer id, Integer categoryId, String name) {
}
