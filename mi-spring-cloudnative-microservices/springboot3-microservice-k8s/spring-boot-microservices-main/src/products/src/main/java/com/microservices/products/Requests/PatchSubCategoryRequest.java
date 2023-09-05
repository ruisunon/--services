package com.microservices.products.Requests;

public record PatchSubCategoryRequest(Integer id, Integer categoryId, String name) {
}
