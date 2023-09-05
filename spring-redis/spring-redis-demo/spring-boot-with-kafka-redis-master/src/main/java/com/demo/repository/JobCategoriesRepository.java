package com.demo.repository;

import com.demo.model.JobCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCategoriesRepository extends JpaRepository<JobCategories, Long> {
}
