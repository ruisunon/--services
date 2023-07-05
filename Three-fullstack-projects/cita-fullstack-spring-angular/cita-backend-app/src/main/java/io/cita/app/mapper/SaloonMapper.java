package io.cita.app.mapper;

import io.cita.app.model.domain.entity.Saloon;
import io.cita.app.model.dto.LocationDto;
import io.cita.app.model.dto.SaloonDto;
import lombok.NonNull;

public interface SaloonMapper {
	
	public static SaloonDto toDto(@NonNull final Saloon saloon) {
		return SaloonDto.builder()
				.id(saloon.getId())
				.identifier(saloon.getIdentifier())
				.code(saloon.getCode())
				.taxRef(saloon.getTaxRef())
				.name(saloon.getName())
				.isPrimary(saloon.getIsPrimary())
				.openingDate(saloon.getOpeningDate())
				.fullAdr(saloon.getFullAdr())
				.iframeGoogleMap(saloon.getIframeGoogleMap())
				.email(saloon.getEmail())
				.locationDto(
					LocationDto.builder()
						.id(saloon.getId())
						.identifier(saloon.getLocation().getIdentifier())
						.zipcode(saloon.getLocation().getZipcode())
						.city(saloon.getLocation().getCity())
						.state(saloon.getLocation().getState())
						.build())
				.build();
	}
	
}




