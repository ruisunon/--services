package com.rycoding.ecommerce.repository.student;

import com.rycoding.ecommerce.entities.student.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepository extends JpaRepository<Fee,Long> {
}
