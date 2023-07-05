package io.cita.app.business.reservation.employee.worker.service;

import io.cita.app.business.reservation.employee.worker.model.TaskBeginEndRequest;
import io.cita.app.business.reservation.employee.worker.model.TaskUpdateDescriptionRequest;
import io.cita.app.model.dto.TaskDto;

public interface WorkerReservationTaskService {
	
	TaskDto fetchAssignedTask(final String username, final Integer reservationId);
	TaskDto updateDescription(final TaskUpdateDescriptionRequest taskUpdateDescriptionRequest);
	TaskDto beginTask(final TaskBeginEndRequest taskBeginRequest);
	TaskDto endTask(final TaskBeginEndRequest taskEndRequest);
	
}



