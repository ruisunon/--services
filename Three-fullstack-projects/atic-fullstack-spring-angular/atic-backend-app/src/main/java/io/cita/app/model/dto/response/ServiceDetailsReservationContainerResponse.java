package io.cita.app.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.cita.app.model.dto.OrderedDetailDto;
import io.cita.app.model.dto.ServiceDetailDto;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.io.Serializable;

@Builder
public record ServiceDetailsReservationContainerResponse(
		
		@JsonProperty("orderedDetails")
		Page<OrderedDetailDto> orderedDetailDtos,
		
		@JsonProperty("serviceDetails")
		Page<ServiceDetailDto> serviceDetailDtos) implements Serializable {}


