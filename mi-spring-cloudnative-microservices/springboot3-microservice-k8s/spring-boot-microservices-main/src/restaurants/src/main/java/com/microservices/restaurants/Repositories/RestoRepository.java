package com.microservices.restaurants.Repositories;

import com.microservices.restaurants.Models.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestoRepository extends MongoRepository<Restaurant, Integer> {
}
