package io.cita.app.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.cita.app.model.dto.OrderedDetailDto;
import io.cita.app.model.dto.ReservationDto;
import io.cita.app.model.dto.ServiceDetailDto;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record OrderedDetailContainerResponse(
		
		@JsonProperty("orderedDetail") OrderedDetailDto orderedDetailDto,
		
		@JsonProperty("reservation") ReservationDto reservationDto,
		
		@JsonProperty("serviceDetail") ServiceDetailDto serviceDetailDto) implements Serializable {}


