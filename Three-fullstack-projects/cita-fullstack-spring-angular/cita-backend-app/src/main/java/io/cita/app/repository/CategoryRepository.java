package io.cita.app.repository;

import io.cita.app.model.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	Optional<Category> findByIdentifier(final String identifier);
	List<Category> findAllBySaloonId(final Integer saloonId);
	
}


