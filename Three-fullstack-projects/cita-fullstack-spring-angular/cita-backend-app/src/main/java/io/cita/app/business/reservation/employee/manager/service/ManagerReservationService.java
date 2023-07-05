package io.cita.app.business.reservation.employee.manager.service;

import io.cita.app.business.reservation.employee.manager.model.ManagerReservationResponse;
import io.cita.app.business.reservation.employee.manager.model.ReservationAssignWorkerRequest;
import io.cita.app.business.reservation.employee.manager.model.ReservationSubWorkerResponse;
import io.cita.app.model.dto.ReservationDto;
import io.cita.app.model.dto.request.ClientPageRequest;

public interface ManagerReservationService {
	
	ManagerReservationResponse fetchAllReservations(final String username);
	ManagerReservationResponse fetchAllReservations(final String username, final ClientPageRequest clientPageRequest);
	ReservationDto cancelReservation(final Integer reservationId);
	ManagerReservationResponse searchAllBySaloonIdLikeKey(final String username, final String key);
	ReservationSubWorkerResponse fetchAllUnassignedSubWorkers(final String username, final Integer reservationId);
	ReservationSubWorkerResponse assignReservationWorkers(final String username, 
			final ReservationAssignWorkerRequest reservationAssignWorkerRequest);
	
}



