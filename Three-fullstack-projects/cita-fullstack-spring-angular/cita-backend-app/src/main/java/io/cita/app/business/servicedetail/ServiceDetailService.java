package io.cita.app.business.servicedetail;

import io.cita.app.model.dto.ServiceDetailDto;
import io.cita.app.model.dto.response.ServiceDetailsReservationContainerResponse;
import org.springframework.data.domain.Page;
import io.cita.app.business.servicedetail.employee.manager.model.ServiceDetailRequest;

import java.util.List;
import java.util.Set;

public interface ServiceDetailService {
	
	List<ServiceDetailDto> findAll();
	ServiceDetailDto findById(final Integer id);
	ServiceDetailDto findByIdentifier(final String identifier);
	Page<ServiceDetailDto> findAllByIds(final Set<Integer> ids);
	ServiceDetailsReservationContainerResponse fetchOrderedServiceDetails(final Integer reservationId);
	ServiceDetailsReservationContainerResponse fetchOrderedServiceDetails(final String reservationIdentifier);
	Page<ServiceDetailDto> findAllByCategoryId(final Integer categoryId);
	List<ServiceDetailDto> findAllByCategorySaloonId(final Integer saloonId);
	ServiceDetailDto save(final ServiceDetailRequest serviceDetailRequest);
	ServiceDetailDto update(final ServiceDetailRequest serviceDetailRequest);
	
}



