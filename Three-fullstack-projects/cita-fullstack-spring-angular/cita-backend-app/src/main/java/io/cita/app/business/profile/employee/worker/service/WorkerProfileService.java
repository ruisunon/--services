package io.cita.app.business.profile.employee.worker.service;

import io.cita.app.business.profile.employee.worker.model.WorkerProfileResponse;
import io.cita.app.model.dto.EmployeeDto;
import io.cita.app.business.profile.employee.worker.model.WorkerProfileRequest;

public interface WorkerProfileService {
	
	WorkerProfileResponse fetchProfile(final String username);
	EmployeeDto updateProfileInfo(final WorkerProfileRequest workerProfileRequest);
	
}



