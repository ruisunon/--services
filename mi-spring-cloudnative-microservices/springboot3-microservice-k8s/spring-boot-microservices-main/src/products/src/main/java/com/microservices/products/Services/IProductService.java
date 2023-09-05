package com.microservices.products.Services;

import com.microservices.products.Models.Product;
import com.microservices.products.Requests.CheckoutOrderRequest;
import com.microservices.products.Requests.CreateProductRequest;
import com.microservices.products.Requests.FindProductRequest;
import com.microservices.products.Requests.PatchProductRequest;

import java.util.List;

public interface IProductService {
    List<Product> GetProducts();
    int DeleteProduct(Integer id);
    Product CreateProduct(CreateProductRequest request);
    List<Product> CheckoutOrder(CheckoutOrderRequest request);
    Product PatchProduct(PatchProductRequest request);
    Product FindProduct(FindProductRequest request);
}
