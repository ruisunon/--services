package io.cita.app.business.reservation.employee.manager.service;

import io.cita.app.business.reservation.employee.manager.model.ManagerWorkerAssignmentResponse;
import io.cita.app.model.dto.request.ClientPageRequest;

public interface ManagerWorkerAssignmentService {
	
	ManagerWorkerAssignmentResponse fetchAllWorkerTasks(final String username,
                                                        final Integer workerId, final ClientPageRequest clientPageRequest);
	ManagerWorkerAssignmentResponse searchAllLikeKey(final String username, final Integer workerId, final String key);
	
}



