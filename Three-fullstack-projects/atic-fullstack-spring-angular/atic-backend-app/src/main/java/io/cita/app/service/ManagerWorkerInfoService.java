package io.cita.app.service;

import io.cita.app.model.dto.EmployeeDto;
import io.cita.app.model.dto.response.ManagerWorkerInfoResponse;

public interface ManagerWorkerInfoService {
	
	ManagerWorkerInfoResponse fetchAllSubWorkers(final String username);
	EmployeeDto fetchWorkerInfo(final Integer workerId);
	
}


