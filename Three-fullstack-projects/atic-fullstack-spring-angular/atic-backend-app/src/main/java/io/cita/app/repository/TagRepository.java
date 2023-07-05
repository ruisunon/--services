package io.cita.app.repository;

import io.cita.app.model.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
	
	Optional<Tag> findByIdentifier(final String identifier);
	
}


