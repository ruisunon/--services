package io.cita.app.mapper;

import io.cita.app.model.domain.entity.ServiceDetail;
import io.cita.app.model.dto.CategoryDto;
import io.cita.app.model.dto.ServiceDetailDto;
import lombok.NonNull;

public interface ServiceDetailMapper {
	
	public static ServiceDetailDto toDto(@NonNull final ServiceDetail serviceDetail) {
		return ServiceDetailDto.builder()
				.id(serviceDetail.getId())
				.identifier(serviceDetail.getIdentifier())
				.name(serviceDetail.getName())
				.description(serviceDetail.getDescription())
				.isAvailable(serviceDetail.getIsAvailable())
				.duration(serviceDetail.getDuration())
				.priceUnit(serviceDetail.getPriceUnit())
				.categoryDto(
					CategoryDto.builder()
						.id(serviceDetail.getCategory().getId())
						.identifier(serviceDetail.getCategory().getIdentifier())
						.name(serviceDetail.getCategory().getName())
						.build())
				.build();
	}
	
}




