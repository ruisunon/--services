package io.rxdata.services.trip.repository;

import io.rxdata.services.trip.model.Trip;

import org.springframework.data.repository.CrudRepository;

public interface TripRepository extends CrudRepository<Trip, Long> {
}
