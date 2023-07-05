package io.cita.app.business.reservation.employee.worker.service;

import io.cita.app.model.dto.response.ReservationDetailResponse;

public interface WorkerReservationDetailService {
	
	ReservationDetailResponse fetchReservationDetails(final Integer reservationId);
	
}



