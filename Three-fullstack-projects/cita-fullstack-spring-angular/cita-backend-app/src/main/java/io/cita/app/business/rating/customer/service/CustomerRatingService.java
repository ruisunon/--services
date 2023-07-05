package io.cita.app.business.rating.customer.service;

import io.cita.app.business.rating.customer.model.CustomerRatingResponse;

public interface CustomerRatingService {
	
	CustomerRatingResponse fetchAllRatings(final String username);
	
}



