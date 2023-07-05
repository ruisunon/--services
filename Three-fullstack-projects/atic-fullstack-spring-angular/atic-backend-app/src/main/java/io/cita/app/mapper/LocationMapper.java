package io.cita.app.mapper;

import io.cita.app.model.domain.entity.Location;
import io.cita.app.model.dto.LocationDto;
import lombok.NonNull;

public interface LocationMapper {
	
	public static LocationDto toDto(@NonNull final Location location) {
		return LocationDto.builder()
				.id(location.getId())
				.identifier(location.getIdentifier())
				.zipcode(location.getZipcode())
				.city(location.getCity())
				.state(location.getState())
				.build();
	}
	
}




