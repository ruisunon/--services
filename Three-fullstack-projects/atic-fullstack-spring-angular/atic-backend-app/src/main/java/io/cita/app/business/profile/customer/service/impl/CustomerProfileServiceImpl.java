package io.cita.app.business.profile.customer.service.impl;

import io.cita.app.business.profile.customer.model.CustomerProfileRequest;
import io.cita.app.business.profile.customer.model.CustomerProfileResponse;
import io.cita.app.business.profile.customer.service.CustomerProfileService;
import io.cita.app.mapper.CustomerMapper;
import io.cita.app.mapper.FavouriteMapper;
import io.cita.app.mapper.RatingMapper;
import io.cita.app.mapper.ReservationMapper;
import io.cita.app.model.dto.CustomerDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.repository.RatingRepository;
import io.cita.app.util.ClientPageRequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import io.cita.app.exception.wrapper.CustomerNotFoundException;
import io.cita.app.exception.wrapper.PasswordNotMatchException;
import io.cita.app.exception.wrapper.UsernameAlreadyExistsException;
import io.cita.app.repository.CustomerRepository;
import io.cita.app.repository.FavouriteRepository;
import io.cita.app.repository.ReservationRepository;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CustomerProfileServiceImpl implements CustomerProfileService {
	
	private final CustomerRepository customerRepository;
	private final ReservationRepository reservationRepository;
	private final FavouriteRepository favouriteRepository;
	private final RatingRepository ratingRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public CustomerProfileResponse fetchProfile(final String username, final ClientPageRequest clientPageRequest) {
		log.info("** Fetch customer profile.. *\n");
		final var customerDto = this.customerRepository
				.findByCredentialUsernameIgnoringCase(username)
				.map(CustomerMapper::toDto)
				.orElseThrow(CustomerNotFoundException::new);
		
		return CustomerProfileResponse.builder()
				.customerDto(customerDto)
				.reservationDtos(this.reservationRepository
						.findAllByCustomerId(
								customerDto.getId(),
								ClientPageRequestUtils.from(clientPageRequest))
						.map(ReservationMapper::toDto))
				.favouriteDtos(this.favouriteRepository
						.findAllByCustomerId(
								customerDto.getId(),
								ClientPageRequestUtils.from(clientPageRequest))
						.map(FavouriteMapper::toDto))
				.ratingDtos(new PageImpl<>(this.ratingRepository
						.findAllByCustomerId(customerDto.getId()).stream()
							.map(RatingMapper::toDto)
							.toList()))
				.build();
	}
	
	@Transactional
	@Override
	public CustomerDto updateProfileInfo(final CustomerProfileRequest customerProfileRequest) {
		
		log.info("** Update customer profile.. *\n");
		
		this.customerRepository
				.findByCredentialUsernameIgnoringCase(customerProfileRequest.username().strip())
				.filter(c -> c.getCredential().getUsername()
						.equals(customerProfileRequest.authenticatedUsername()))
				.ifPresent(c -> {
			throw new UsernameAlreadyExistsException("Username already exists, please choose another");
		});
		
		if (!customerProfileRequest.password().equals(customerProfileRequest.confirmPassword()))
			throw new PasswordNotMatchException("Passwords do not matched.. please confirm");
		
		final var authenticatedCustomer = this.customerRepository
				.findByCredentialUsernameIgnoringCase(customerProfileRequest.authenticatedUsername())
				.orElseThrow(() -> new CustomerNotFoundException("Customer with username: %s not found"
						.formatted(customerProfileRequest.authenticatedUsername())));
		authenticatedCustomer.setFirstname(customerProfileRequest.firstname().strip());
		authenticatedCustomer.setLastname(customerProfileRequest.lastname().strip());
		authenticatedCustomer.setEmail(customerProfileRequest.email().strip());
		authenticatedCustomer.setPhone(customerProfileRequest.phone().strip());
		authenticatedCustomer.setBirthdate(customerProfileRequest.birthdate());
		authenticatedCustomer.setFacebookUrl(StringUtils
				.trimAllWhitespace(customerProfileRequest.facebookUrl()));
		authenticatedCustomer.setInstagramUrl(StringUtils
				.trimAllWhitespace(customerProfileRequest.instagramUrl()));
		authenticatedCustomer.setLinkedinUrl(StringUtils
				.trimAllWhitespace(customerProfileRequest.linkedinUrl()));
		authenticatedCustomer.getCredential().setUsername(customerProfileRequest.username().strip().toLowerCase());
		authenticatedCustomer.getCredential().setPassword(this.passwordEncoder.encode(customerProfileRequest.password()));
		
		return CustomerMapper.toDto(this.customerRepository.save(authenticatedCustomer));
	}
	
}





