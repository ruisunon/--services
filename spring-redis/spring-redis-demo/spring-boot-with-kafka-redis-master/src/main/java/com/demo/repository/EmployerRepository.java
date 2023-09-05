package com.demo.repository;

import com.demo.model.Employee;
import com.demo.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Employer findByPhoneNumber(int phoneNumber);
}
