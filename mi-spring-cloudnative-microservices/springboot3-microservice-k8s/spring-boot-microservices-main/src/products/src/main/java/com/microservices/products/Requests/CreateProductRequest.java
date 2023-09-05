package com.microservices.products.Requests;

public record CreateProductRequest(Integer id,Integer subCategoryId, String name, String description, Double price) {}
