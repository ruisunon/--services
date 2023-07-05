package io.cita.app.service.impl;

import io.cita.app.mapper.RatingMapper;
import io.cita.app.model.dto.RatingDto;
import io.cita.app.repository.RatingRepository;
import io.cita.app.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
	
	private final RatingRepository ratingRepository;
	
	@Override
	public List<RatingDto> findAllByCustomerId(final Integer customerId) {
		log.info("** Find all ratings by customerId.. *");
		return this.ratingRepository
				.findAllByCustomerId(customerId).stream()
					.map(RatingMapper::toDto)
					.toList();
	}
	
}



