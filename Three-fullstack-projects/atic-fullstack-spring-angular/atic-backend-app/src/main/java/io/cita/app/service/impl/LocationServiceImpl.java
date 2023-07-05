package io.cita.app.service.impl;

import io.cita.app.mapper.LocationMapper;
import io.cita.app.model.domain.entity.Location;
import io.cita.app.model.dto.LocationDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.repository.LocationRepository;
import io.cita.app.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.LocationNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
	
	private final LocationRepository locationRepository;
	
	@Override
	public Page<LocationDto> findAll(final ClientPageRequest clientPageRequest) {
		log.info("** Find all paged locations.. *");
		return this.locationRepository
				.findAll(PageRequest.of(
						clientPageRequest.getOffset() - 1, clientPageRequest.getSize()))
				.map(LocationMapper::toDto);
	}
	
	@Override
	public LocationDto findById(final Integer id) {
		log.info("** Find location by id.. *");
		return this.locationRepository.findById(id)
				.map(LocationMapper::toDto)
				.orElseThrow(LocationNotFoundException::new);
	}
	
	@Override
	public List<String> fetchAllCities() {
		log.info("** Fetch all cities.. *");
		return this.locationRepository.findAll().stream()
				.map(Location::getCity)
				.map(String::toLowerCase)
				.distinct()
				.toList();
	}
	
	@Override
	public List<String> fetchAllStates() {
		log.info("** Fetch all states.. *");
		return this.locationRepository.findAll().stream()
				.map(Location::getState)
				.map(String::toLowerCase)
				.distinct()
				.toList();
	}
	
}




