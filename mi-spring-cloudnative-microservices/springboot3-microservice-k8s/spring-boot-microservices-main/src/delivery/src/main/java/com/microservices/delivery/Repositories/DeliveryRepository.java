package com.microservices.delivery.Repositories;

import com.microservices.delivery.Models.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliveryRepository extends MongoRepository<Delivery, Integer> {
}
