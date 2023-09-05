package com.microservices.products.Requests;

public record PatchProductRequest(Integer id,String name, String description, Double price,Integer subCategoryId) {
}
