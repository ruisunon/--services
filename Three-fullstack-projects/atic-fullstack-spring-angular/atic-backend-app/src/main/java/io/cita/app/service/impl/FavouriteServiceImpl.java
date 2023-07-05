package io.cita.app.service.impl;

import io.cita.app.mapper.FavouriteMapper;
import io.cita.app.model.domain.id.FavouriteId;
import io.cita.app.model.dto.FavouriteDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.service.FavouriteService;
import io.cita.app.util.ClientPageRequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.FavouriteNotFoundException;
import io.cita.app.repository.FavouriteRepository;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {
	
	private final FavouriteRepository favouriteRepository;
	
	@Override
	public FavouriteDto findById(final FavouriteId favouriteId) {
		return this.favouriteRepository.findById(favouriteId)
				.map(FavouriteMapper::toDto)
				.orElseThrow(FavouriteNotFoundException::new);
	}
	
	@Override
	public Page<FavouriteDto> findAllByCustomerId(final Integer customerId, final ClientPageRequest clientPageRequest) {
		log.info("** Find all favourites by customerId.. *");
		return this.favouriteRepository.findAllByCustomerId(customerId, 
					ClientPageRequestUtils.from(clientPageRequest))
				.map(FavouriteMapper::toDto);
	}
	
	@Transactional
	@Override
	public Boolean deleteById(final FavouriteId favouriteId) {
		log.info("** Delete favourite by id.. *");
		this.favouriteRepository.deleteById(favouriteId);
		return !this.favouriteRepository.existsById(favouriteId);
	}
	
}



