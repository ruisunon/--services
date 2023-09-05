package com.coderxs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderxs.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}

