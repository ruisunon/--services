package io.cita.app.business.reservation;

import io.cita.app.model.dto.ReservationDto;
import io.cita.app.business.reservation.employee.manager.model.ReservationAssignWorkerRequest;
import io.cita.app.business.reservation.employee.manager.model.ReservationSubWorkerResponse;

public interface ReservationCommonService {
	
	ReservationDto cancelReservation(final Integer reservationId);
	ReservationSubWorkerResponse fetchAllUnassignedSubWorkers(final String username, final Integer reservationId);
	ReservationSubWorkerResponse assignReservationWorkers(final String username, 
			final ReservationAssignWorkerRequest reservationAssignWorkerRequest);
	
}



