package io.cita.app.business.reservation.customer.service;

import io.cita.app.model.dto.ReservationDto;
import io.cita.app.model.dto.request.ReservationDetailRequest;
import io.cita.app.model.dto.response.ReservationDetailResponse;

public interface CustomerReservationDetailService {
	
	ReservationDetailResponse fetchReservationDetails(final Integer reservationId);
	ReservationDetailResponse fetchReservationDetails(final String reservationIdentifier);
	ReservationDto updateReservationDetails(final ReservationDetailRequest reservationDetailRequest);
	
}



