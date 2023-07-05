package io.cita.app.business.profile.employee.worker.service.impl;

import io.cita.app.business.profile.employee.worker.model.WorkerProfileResponse;
import io.cita.app.mapper.EmployeeMapper;
import io.cita.app.model.dto.EmployeeDto;
import io.cita.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.business.profile.employee.worker.model.WorkerProfileRequest;
import io.cita.app.business.profile.employee.worker.service.WorkerProfileService;
import io.cita.app.exception.wrapper.EmployeeNotFoundException;
import io.cita.app.exception.wrapper.PasswordNotMatchException;
import io.cita.app.exception.wrapper.UsernameAlreadyExistsException;
import io.cita.app.repository.EmployeeRepository;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class WorkerProfileServiceImpl implements WorkerProfileService {
	
	private final EmployeeRepository employeeRepository;
	private final TaskService taskRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public WorkerProfileResponse fetchProfile(final String username) {
		log.info("** Fetch worker profile.. *\n");
		final var workerDto = this.employeeRepository
				.findByCredentialUsernameIgnoringCase(username)
				.map(EmployeeMapper::toDto)
				.orElseThrow(EmployeeNotFoundException::new);
		return new WorkerProfileResponse(
				workerDto, 
				workerDto.getCredentialDto(), 
				new PageImpl<>(this.taskRepository
						.findAllByWorkerId(workerDto.getId())));
	}
	
	@Transactional
	@Override
	public EmployeeDto updateProfileInfo(final WorkerProfileRequest workerProfileRequest) {
		log.info("** Update worker profile.. *\n");

		this.employeeRepository
				.findByCredentialUsernameIgnoringCase(workerProfileRequest.username().strip())
				.filter(c -> c.getCredential().getUsername()
						.equals(workerProfileRequest.authenticatedUsername()))
				.ifPresent(c -> {
			throw new UsernameAlreadyExistsException("Username already exists, please choose another");
		});
		
		if (!workerProfileRequest.password().equals(workerProfileRequest.confirmPassword()))
			throw new PasswordNotMatchException("Passwords are not matched.. please confirm");
		
		final var authenticatedWorker = this.employeeRepository
				.findByCredentialUsernameIgnoringCase(workerProfileRequest.authenticatedUsername().strip())
				.orElseThrow(() -> new EmployeeNotFoundException("Employee with username: %s not found"
						.formatted(workerProfileRequest.authenticatedUsername())));
		authenticatedWorker.setFirstname(workerProfileRequest.firstname().strip());
		authenticatedWorker.setLastname(workerProfileRequest.lastname().strip());
		authenticatedWorker.setEmail(workerProfileRequest.email().strip());
		authenticatedWorker.setPhone(workerProfileRequest.phone().strip());
		authenticatedWorker.setBirthdate(workerProfileRequest.birthdate());
		authenticatedWorker.setHiredate(workerProfileRequest.hiredate());
		authenticatedWorker.getCredential().setUsername(workerProfileRequest.username().strip().toLowerCase());
		authenticatedWorker.getCredential().setPassword(this.passwordEncoder.encode(workerProfileRequest.password()));
		
		return EmployeeMapper.toDto(this.employeeRepository.save(authenticatedWorker));
	}
	
}




