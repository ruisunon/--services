package io.cita.app.repository;

import io.cita.app.model.domain.entity.SaloonImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaloonImageRepository extends JpaRepository<SaloonImage, Integer> {
	
	Optional<SaloonImage> findByIdentifier(final String identifier);
	
}



