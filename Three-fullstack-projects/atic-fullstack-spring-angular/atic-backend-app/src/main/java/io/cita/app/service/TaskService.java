package io.cita.app.service;

import io.cita.app.model.domain.id.TaskId;
import io.cita.app.model.dto.TaskDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {
	
	TaskDto findById(final TaskId taskId);
	TaskDto findByIdentifier(final String identifier);
	List<TaskDto> findAllByReservationId(final Integer reservationId);
	List<TaskDto> findAllByWorkerId(final Integer workerId);
	Page<TaskDto> findAllByWorkerId(final Integer workerId, final ClientPageRequest clientPageRequest);
	
}



