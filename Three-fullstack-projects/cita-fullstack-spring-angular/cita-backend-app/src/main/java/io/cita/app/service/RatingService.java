package io.cita.app.service;

import io.cita.app.model.dto.RatingDto;

import java.util.List;

public interface RatingService {
	
	List<RatingDto> findAllByCustomerId(final Integer customerId);
	
}


