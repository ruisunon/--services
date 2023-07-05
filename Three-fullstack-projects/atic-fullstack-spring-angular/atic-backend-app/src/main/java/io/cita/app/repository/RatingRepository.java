package io.cita.app.repository;

import io.cita.app.model.domain.id.RatingId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import io.cita.app.model.domain.entity.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, RatingId> {
	
	Optional<Rating> findByIdentifier(final String identifier);
	List<Rating> findAllByCustomerId(final Integer customerId);
	Page<Rating> findAllByCustomerId(final Integer customerId, final Pageable pageable);
	List<Rating> findAllByWorkerId(final Integer workerId);
	Page<Rating> findAllByWorkerId(final Integer workerId, final Pageable pageable);
	
}



