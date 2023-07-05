package io.cita.app.model.domain.listener;

import io.cita.app.model.domain.entity.Rating;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class RatingEntityListener {
	
	@PrePersist
	public void preCreate(final Rating rating) {
		rating.setRateDate(LocalDateTime.now());
	}
	
}



