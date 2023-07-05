package io.cita.app.service;

import io.cita.app.model.domain.id.OrderedDetailId;
import io.cita.app.model.dto.OrderedDetailDto;
import io.cita.app.model.dto.request.OrderedDetailRequest;

import java.util.List;

public interface OrderedDetailService {
	
	OrderedDetailDto findByIdentifier(final String identifier);
	List<OrderedDetailDto> findAllByReservationId(final Integer reservationId);
	Boolean deleteById(final OrderedDetailId orderedDetailId);
	OrderedDetailDto save(final OrderedDetailRequest orderedDetailRequest);
	
}



