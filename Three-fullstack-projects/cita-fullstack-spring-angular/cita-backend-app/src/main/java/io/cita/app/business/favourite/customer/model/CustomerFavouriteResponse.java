package io.cita.app.business.favourite.customer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.cita.app.model.dto.CustomerDto;
import io.cita.app.model.dto.FavouriteDto;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public record CustomerFavouriteResponse(
		
		@JsonProperty("customer") CustomerDto customerDto,
		
		@JsonInclude(value = Include.NON_NULL)
		@JsonProperty("favourites")
		Page<FavouriteDto> favouriteDtos) implements Serializable {}


