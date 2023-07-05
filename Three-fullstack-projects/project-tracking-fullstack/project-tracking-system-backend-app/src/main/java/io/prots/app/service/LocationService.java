package io.prots.app.service;

import io.prots.app.model.dto.collection.DtoCollection;
import io.prots.app.model.entity.Location;

public interface LocationService {
	
	DtoCollection<Location> findAll();
	Location findById(final Integer locationId);
	Location save(final Location location);
	Location update(final Location location);
	void deleteById(final Integer locationId);
	
}
