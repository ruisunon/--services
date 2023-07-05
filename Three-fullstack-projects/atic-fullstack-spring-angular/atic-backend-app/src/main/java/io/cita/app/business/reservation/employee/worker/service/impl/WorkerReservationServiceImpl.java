package io.cita.app.business.reservation.employee.worker.service.impl;

import io.cita.app.business.reservation.employee.worker.service.WorkerReservationService;
import io.cita.app.mapper.EmployeeMapper;
import io.cita.app.mapper.TaskMapper;
import io.cita.app.model.dto.EmployeeDto;
import io.cita.app.model.dto.TaskDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.repository.EmployeeRepository;
import io.cita.app.repository.TaskRepository;
import io.cita.app.util.ClientPageRequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.EmployeeNotFoundException;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class WorkerReservationServiceImpl implements WorkerReservationService {
	
	private final EmployeeRepository employeeRepository;
	private final TaskRepository taskRepository;
	
	@Override
	public Page<TaskDto> fetchAllReservations(final String username, final ClientPageRequest clientPageRequest) {
		log.info("** Fetch all paged reservations by worker.. *");
		final var workerDto = this.retrieveWorkerByUsername(username.strip());
		return this.taskRepository
				.findAllByWorkerId(workerDto.getId(), ClientPageRequestUtils.from(clientPageRequest))
				.map(TaskMapper::toDto);
	}
	
	@Override
	public Page<TaskDto> fetchAllReservations(final String username) {
		log.info("** Fetch all reservations by worker.. *");
		final var workerDto = this.retrieveWorkerByUsername(username.strip());
		return new PageImpl<>(this.taskRepository.findAllByWorkerId(workerDto.getId()))
				.map(TaskMapper::toDto);
	}
	
	@Override
	public Page<TaskDto> searchAllLikeKey(final String username, final String key) {
		log.info("** Search all reservations like key by worker.. *");
		final var workerDto = this.retrieveWorkerByUsername(username.strip());
		return new PageImpl<>(this.taskRepository
				.searchAllByWorkerIdLikeKey(workerDto.getId(), key.strip().toLowerCase()).stream()
					.map(TaskMapper::toDto)
					.toList());
	}
	
	private EmployeeDto retrieveWorkerByUsername(final String username) {
		return this.employeeRepository
				.findByCredentialUsernameIgnoringCase(username)
				.map(EmployeeMapper::toDto)
				.orElseThrow(() -> new EmployeeNotFoundException(
						"Employee with username: %s not found".formatted(username)));
	}
	
}



