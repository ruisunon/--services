package io.cita.app.business.servicedetail.employee.manager.service;

import io.cita.app.model.dto.ServiceDetailDto;
import org.springframework.data.domain.Page;
import io.cita.app.business.servicedetail.employee.manager.model.ServiceDetailRequest;

public interface ManagerServiceDetailService {
	
	Page<ServiceDetailDto> fetchAll(final String username);
	ServiceDetailDto fetchById(final Integer serviceDetailId);
	Boolean deleteServiceDetail(final Integer serviceDetailId);
	ServiceDetailDto saveServiceDetail(final ServiceDetailRequest serviceDetailRequest);
	ServiceDetailDto updateServiceDetail(final ServiceDetailRequest serviceDetailRequest);
	
}



