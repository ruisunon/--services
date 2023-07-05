package io.cita.app.business.rating.customer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.cita.app.model.dto.CustomerDto;
import io.cita.app.model.dto.RatingDto;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.io.Serializable;

@Builder
public record CustomerRatingResponse(
		
		@JsonProperty("customer") CustomerDto customerDto,
		
		@JsonInclude(value = Include.NON_NULL)
		@JsonProperty("ratings")
		Page<RatingDto> ratingDtos) implements Serializable {}


