package io.cita.app.business.profile.employee.manager.service;

import io.cita.app.business.profile.employee.manager.model.ManagerProfileRequest;
import io.cita.app.business.profile.employee.manager.model.ManagerProfileResponse;
import io.cita.app.model.dto.EmployeeDto;

public interface ManagerProfileService {
	
	ManagerProfileResponse fetchProfile(final String username);
	EmployeeDto updateProfileInfo(final ManagerProfileRequest managerProfileRequest);
	
}



