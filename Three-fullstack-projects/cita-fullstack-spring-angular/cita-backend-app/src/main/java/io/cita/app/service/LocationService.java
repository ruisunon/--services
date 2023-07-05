package io.cita.app.service;

import io.cita.app.model.dto.LocationDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LocationService {
	
	Page<LocationDto> findAll(final ClientPageRequest clientPageRequest);
	LocationDto findById(final Integer id);
	List<String> fetchAllCities();
	List<String> fetchAllStates();
	
}



