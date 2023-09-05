package io.rxdata.services.driver.repository;

import io.rxdata.services.driver.model.Driver;

import org.springframework.data.repository.CrudRepository;

public interface DriverRepository extends CrudRepository<Driver, Long> {
}
