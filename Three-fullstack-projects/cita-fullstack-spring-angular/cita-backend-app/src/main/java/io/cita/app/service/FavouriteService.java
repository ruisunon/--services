package io.cita.app.service;

import io.cita.app.model.domain.id.FavouriteId;
import io.cita.app.model.dto.FavouriteDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import org.springframework.data.domain.Page;

public interface FavouriteService {
	
	FavouriteDto findById(final FavouriteId favouriteId);
	Page<FavouriteDto> findAllByCustomerId(final Integer customerId, final ClientPageRequest clientPageRequest);
	Boolean deleteById(final FavouriteId favouriteId);
	
}



