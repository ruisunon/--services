package com.microservices.orders.Controllers;

import com.microservices.orders.Models.ProductOrder;
import com.microservices.orders.Services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrdersController {

    @Autowired
    private OrdersService service;

    @GetMapping
    public ResponseEntity<List<ProductOrder>> GetAll(){
        List<ProductOrder> result = service.GetAll();

        return  ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Object> Insert(@RequestBody ProductOrder product){

        if(product==null)
            return ResponseEntity.badRequest().body("Please enter valid product");

        int result = service.InsertOne(product);

        if(result==0){
            return ResponseEntity.badRequest().body("No instances were created");
        }

        return ResponseEntity.ok("All good");
    }
}
