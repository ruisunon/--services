package io.cita.app.service;

import io.cita.app.model.dto.SaloonDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import org.springframework.data.domain.Page;

public interface SaloonService {
	
	Page<SaloonDto> findAll(final ClientPageRequest clientPageRequest);
	Page<SaloonDto> findAllByLocationState(final String state, final ClientPageRequest clientPageRequest);
	SaloonDto findById(final Integer id);
	SaloonDto findByIdentifier(final String identifier);
	Page<SaloonDto> findAllByCode(final String code);
	
}



