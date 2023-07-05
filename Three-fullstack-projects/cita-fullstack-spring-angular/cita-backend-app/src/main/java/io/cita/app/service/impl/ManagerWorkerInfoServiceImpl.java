package io.cita.app.service.impl;

import io.cita.app.mapper.EmployeeMapper;
import io.cita.app.model.dto.EmployeeDto;
import io.cita.app.model.dto.response.ManagerWorkerInfoResponse;
import io.cita.app.service.ManagerWorkerInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.EmployeeNotFoundException;
import io.cita.app.repository.EmployeeRepository;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ManagerWorkerInfoServiceImpl implements ManagerWorkerInfoService {
	
	private final EmployeeRepository employeeRepository;
	
	@Override
	public ManagerWorkerInfoResponse fetchAllSubWorkers(final String username) {
		log.info("** Fetch all sub workers by manager.. *");
		final var managerDto = this.employeeRepository
				.findByCredentialUsernameIgnoringCase(username)
				.map(EmployeeMapper::toDto)
				.orElseThrow(() -> new EmployeeNotFoundException(
						"Employee with username: %s not found".formatted(username)));
		return new ManagerWorkerInfoResponse(managerDto,
				new PageImpl<>(this.employeeRepository
						.findAllByManagerId(managerDto.getId()).stream()
						.map(EmployeeMapper::toDto)
						.toList()));
	}
	
	@Override
	public EmployeeDto fetchWorkerInfo(final Integer workerId) {
		log.info("** Fetch worker info.. *");
		return this.employeeRepository.findById(workerId)
				.map(EmployeeMapper::toDto)
				.orElseThrow(EmployeeNotFoundException::new);
	}
	
}



