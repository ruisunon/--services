package com.microservices.orders.Services;

import com.microservices.orders.Models.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    @Autowired
    private JdbcTemplate template;
    
    public List<ProductOrder> GetAll(){
        String sql = "SELECT * FROM productorder";

        var result = template.query(sql, BeanPropertyRowMapper.newInstance(ProductOrder.class));

        return result;
    }

    public int InsertOne(ProductOrder order){
        String sql = """
                INSERT INTO productorder(date, total_order_price) VALUES (?,?)
                """;

        var result = template.update(sql, order.getDate(), order.getTotal_order_price());



        return result;
    }
}
