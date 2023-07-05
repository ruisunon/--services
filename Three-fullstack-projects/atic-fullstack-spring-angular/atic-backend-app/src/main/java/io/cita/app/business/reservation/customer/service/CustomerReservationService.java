package io.cita.app.business.reservation.customer.service;

import io.cita.app.model.dto.ReservationDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.model.dto.request.ReservationRequest;
import io.cita.app.business.reservation.customer.model.CustomerReservationResponse;

public interface CustomerReservationService {
	
	CustomerReservationResponse fetchAllReservations(final String username, final ClientPageRequest clientPageRequest);
	ReservationDto cancelReservation(final Integer reservationId);
	ReservationDto createReservation(final ReservationRequest reservationRequest);
	CustomerReservationResponse searchAllByCustomerIdLikeKey(final String username, final String key);
	
}



