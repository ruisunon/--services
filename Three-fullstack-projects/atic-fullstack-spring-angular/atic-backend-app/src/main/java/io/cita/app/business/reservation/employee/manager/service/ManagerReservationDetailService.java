package io.cita.app.business.reservation.employee.manager.service;

import io.cita.app.business.reservation.employee.manager.model.ReservationAssignWorkerRequest;
import io.cita.app.business.reservation.employee.manager.model.ReservationSubWorkerResponse;
import io.cita.app.model.dto.response.ReservationBeginEndTask;
import io.cita.app.model.dto.response.ReservationDetailResponse;

public interface ManagerReservationDetailService {
	
	ReservationDetailResponse fetchReservationDetails(final Integer reservationId);
	ReservationBeginEndTask fetchBeginEndTask(final Integer reservationId);
	ReservationSubWorkerResponse fetchAllUnassignedSubWorkers(final String username, final Integer reservationId);
	ReservationSubWorkerResponse assignReservationWorkers(final String username, 
			final ReservationAssignWorkerRequest reservationAssignWorkerRequest);
	
}



