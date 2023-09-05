package com.microservices.products.Services;

import com.microservices.products.DAOs.ProductDao;
import com.microservices.products.DAOs.ProductDaoImpl;
import com.microservices.products.Models.Product;
import com.microservices.products.Requests.CheckoutOrderRequest;
import com.microservices.products.Requests.CreateProductRequest;
import com.microservices.products.Requests.FindProductRequest;
import com.microservices.products.Requests.PatchProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    private ProductDao productDao;

    public List<Product> GetProducts(){
        var result = productDao.GetAll();

        return  result;
    }

    @Override
    public Product FindProduct(FindProductRequest request) {
        var result = productDao.FindProduct(request);

        return  result;
    }

    @Override
    public int DeleteProduct(Integer id) {
        var result = productDao.Delete(id);

        return  result;
    }

    public Product CreateProduct(CreateProductRequest request){

      Product product = Product
              .builder()
              .description(request.description())
              .price(request.price())
              .name(request.name())
              .id(request.id())
              .subCategoryId(request.subCategoryId())
              .build();

      var result = productDao.Save(product);

      return result;
    }

    @Override
    public Product PatchProduct(PatchProductRequest request) {
        Product product = Product
                .builder()
                .description(request.description())
                .price(request.price())
                .name(request.name())
                .id(request.id())
                .subCategoryId(request.subCategoryId())
                .build();

        var result = productDao.Patch(product);

        return result;
    }

    @Override
    public List<Product> CheckoutOrder(CheckoutOrderRequest request){
        //kafkaTemplate.send("orderTopic", "Hello from user : "+request.getUser_id()+" length of product list is ->"+request.getProducts().size());
        return request.getProducts();
    }
}

