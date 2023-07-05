package io.cita.app.mapper;

import io.cita.app.model.domain.entity.OrderedDetail;
import io.cita.app.model.dto.OrderedDetailDto;
import io.cita.app.model.dto.ReservationDto;
import io.cita.app.model.dto.ServiceDetailDto;
import lombok.NonNull;

public interface OrderedDetailMapper {
	
	public static OrderedDetailDto toDto(@NonNull final OrderedDetail orderedDetail) {
		return OrderedDetailDto.builder()
				.reservationId(orderedDetail.getReservationId())
				.serviceDetailId(orderedDetail.getServiceDetailId())
				.orderedDate(orderedDetail.getOrderedDate())
				.identifier(orderedDetail.getIdentifier())
				.reservationDto(
					ReservationDto.builder()
						.id(orderedDetail.getReservation().getId())
						.identifier(orderedDetail.getReservation().getIdentifier())
						.code(orderedDetail.getReservation().getCode())
						.description(orderedDetail.getReservation().getDescription())
						.startDate(orderedDetail.getReservation().getStartDate())
						.cancelDate(orderedDetail.getReservation().getCancelDate())
						.completeDate(orderedDetail.getReservation().getCompleteDate())
						.status(orderedDetail.getReservation().getStatus())
						.build())
				.serviceDetailDto(
					ServiceDetailDto.builder()
						.id(orderedDetail.getServiceDetail().getId())
						.identifier(orderedDetail.getServiceDetail().getIdentifier())
						.name(orderedDetail.getServiceDetail().getName())
						.description(orderedDetail.getServiceDetail().getDescription())
						.isAvailable(orderedDetail.getServiceDetail().getIsAvailable())
						.duration(orderedDetail.getServiceDetail().getDuration())
						.priceUnit(orderedDetail.getServiceDetail().getPriceUnit())
						.build())
				.build();
	}
	
}




