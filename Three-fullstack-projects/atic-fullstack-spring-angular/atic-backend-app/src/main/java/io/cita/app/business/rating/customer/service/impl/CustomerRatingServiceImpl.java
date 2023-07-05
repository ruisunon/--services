package io.cita.app.business.rating.customer.service.impl;

import io.cita.app.business.rating.customer.model.CustomerRatingResponse;
import io.cita.app.mapper.CustomerMapper;
import io.cita.app.mapper.RatingMapper;
import io.cita.app.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.business.rating.customer.service.CustomerRatingService;
import io.cita.app.exception.wrapper.CustomerNotFoundException;
import io.cita.app.repository.CustomerRepository;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CustomerRatingServiceImpl implements CustomerRatingService {
	
	private final CustomerRepository customerRepository;
	private final RatingRepository ratingRepository;
	
	@Override
	public CustomerRatingResponse fetchAllRatings(final String username) {
		log.info("** Fetch all ratings by customer.. *\n");
		final var customerDto = this.customerRepository
				.findByCredentialUsernameIgnoringCase(username)
				.map(CustomerMapper::toDto)
				.orElseThrow(() ->
						new CustomerNotFoundException("Customer with username: %s not found".formatted(username)));
		return new CustomerRatingResponse(
				customerDto,
				new PageImpl<>(this.ratingRepository
						.findAllByCustomerId(customerDto.getId()).stream()
							.map(RatingMapper::toDto)
							.toList()));
	}
	
}





