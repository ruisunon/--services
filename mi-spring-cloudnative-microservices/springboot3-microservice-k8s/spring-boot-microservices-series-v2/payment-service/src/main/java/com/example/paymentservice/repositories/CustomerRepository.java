/* Licensed under Apache-2.0 2022 */
package com.example.paymentservice.repositories;

import com.example.paymentservice.entities.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByName(String name);
}
