package io.cita.app.business.favourite.customer.service;

import io.cita.app.model.dto.FavouriteDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.business.favourite.customer.model.CustomerFavouriteResponse;

public interface CustomerFavouriteService {
	
	CustomerFavouriteResponse fetchAllFavourites(final String username, final ClientPageRequest clientPageRequest);
	Boolean deleteFavourite(final String username, final Integer saloonId);
	FavouriteDto addFavourite(final String username, final Integer saloonId);
	
}



