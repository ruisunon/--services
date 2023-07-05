package io.cita.app.model.domain.listener;

import io.cita.app.model.domain.entity.Favourite;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class FavouriteEntityListener {
	
	@PrePersist
	public void preCreate(final Favourite favourite) {
		favourite.setFavouriteDate(LocalDateTime.now());
	}
	
}



