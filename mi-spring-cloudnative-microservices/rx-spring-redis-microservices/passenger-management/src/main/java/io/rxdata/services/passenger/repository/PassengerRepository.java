package io.rxdata.services.passenger.repository;

import io.rxdata.services.passenger.model.Passenger;

import org.springframework.data.repository.CrudRepository;

public interface PassengerRepository extends CrudRepository<Passenger, Long> {
}
