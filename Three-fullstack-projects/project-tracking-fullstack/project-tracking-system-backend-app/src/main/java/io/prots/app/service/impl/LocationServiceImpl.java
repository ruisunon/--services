package io.prots.app.service.impl;

import java.util.Collections;

import jakarta.transaction.Transactional;

import io.prots.app.exception.custom.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.prots.app.model.dto.collection.DtoCollection;
import io.prots.app.model.entity.Location;
import io.prots.app.repository.LocationRepository;
import io.prots.app.service.LocationService;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {
	
	private final LocationRepository rep;
	private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);
	
	static {
		logger.info("************ entering " + LocationServiceImpl.class.getName() + " ************");
	}
	
	@Autowired
	public LocationServiceImpl(final LocationRepository rep) {
		this.rep = rep;
	}
	
	@Override
	public DtoCollection<Location> findAll() {
		return new DtoCollection<>(Collections.unmodifiableList(this.rep.findAll()));
	}
	
	@Override
	public Location findById(final Integer locationId) {
		return this.rep.findById(locationId).orElseThrow(() -> new ObjectNotFoundException("###### NO Location object FOUND! ######"));
	}
	
	@Override
	public Location save(final Location location) {
		return this.rep.save(location);
	}
	
	@Override
	public Location update(final Location location) {
		return this.rep.save(location);
	}
	
	@Override
	public void deleteById(final Integer locationId) {
		this.rep.delete(this.findById(locationId));
	}
	
	
	
}










