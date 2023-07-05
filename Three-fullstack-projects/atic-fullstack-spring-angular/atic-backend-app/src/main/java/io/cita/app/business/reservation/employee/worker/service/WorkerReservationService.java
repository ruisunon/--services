package io.cita.app.business.reservation.employee.worker.service;

import io.cita.app.model.dto.TaskDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import org.springframework.data.domain.Page;

public interface WorkerReservationService {
	
	Page<TaskDto> fetchAllReservations(final String username, final ClientPageRequest clientPageRequest);
	Page<TaskDto> fetchAllReservations(final String username);
	Page<TaskDto> searchAllLikeKey(final String username, final String key);
	
}



