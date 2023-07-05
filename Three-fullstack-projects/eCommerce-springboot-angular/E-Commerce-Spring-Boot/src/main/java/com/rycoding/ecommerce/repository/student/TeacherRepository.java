package com.rycoding.ecommerce.repository.student;

import com.rycoding.ecommerce.entities.student.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Page<Teacher> findAll(Pageable pageable);
    @Query(value="SELECT t FROM teachers t WHERE t.name LIKE %?1%", nativeQuery=true)
    List<Teacher> findByNameLike(String title);
    @Query(value = "SELECT t FROM teachers t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery=true)
    List<Teacher> findByNameLikeCaseInsensitive(String title);
}
