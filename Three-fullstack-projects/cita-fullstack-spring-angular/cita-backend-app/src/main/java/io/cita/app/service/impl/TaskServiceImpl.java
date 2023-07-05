package io.cita.app.service.impl;

import io.cita.app.mapper.TaskMapper;
import io.cita.app.model.domain.id.TaskId;
import io.cita.app.model.dto.TaskDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.service.TaskService;
import io.cita.app.util.ClientPageRequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.TaskNotFoundException;
import io.cita.app.repository.TaskRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
	
	private final TaskRepository taskRepository;
	
	@Override
	public TaskDto findById(final TaskId taskId) {
		log.info("** Find task by id.. *");
		return this.taskRepository.findById(taskId)
				.map(TaskMapper::toDto)
				.orElseThrow(TaskNotFoundException::new);
	}
	
	@Override
	public TaskDto findByIdentifier(final String identifier) {
		return this.taskRepository.findByIdentifier(identifier.strip())
				.map(TaskMapper::toDto)
				.orElseThrow(TaskNotFoundException::new);
	}
	
	@Override
	public List<TaskDto> findAllByReservationId(final Integer reservationId) {
		log.info("** Find all tasks by reservationId.. *");
		return this.taskRepository
				.findAllByReservationId(reservationId).stream()
					.map(TaskMapper::toDto)
					.toList();
	}
	
	@Override
	public List<TaskDto> findAllByWorkerId(final Integer workerId) {
		log.info("** Find all tasls by workerId.. *");
		return this.taskRepository
				.findAllByWorkerId(workerId).stream()
					.map(TaskMapper::toDto)
					.toList();
	}
	
	@Override
	public Page<TaskDto> findAllByWorkerId(final Integer workerId, final ClientPageRequest clientPageRequest) {
		log.info("** Find all paged tasks by workerId.. *");
		return this.taskRepository.findAllByWorkerId(
					workerId, ClientPageRequestUtils.from(clientPageRequest))
				.map(TaskMapper::toDto);
	}
	
}



