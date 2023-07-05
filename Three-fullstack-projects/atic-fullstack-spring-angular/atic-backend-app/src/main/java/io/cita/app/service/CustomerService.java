package io.cita.app.service;

import io.cita.app.model.dto.CustomerDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
	
	Page<CustomerDto> findAll(final ClientPageRequest clientPageRequest);
	CustomerDto findById(final Integer id);
	CustomerDto findByIdentifier(final String identifier);
	CustomerDto findByCredentialUsername(final String username);
	List<CustomerDto> findAllBySsn(final String ssn);
	boolean deleteById(final Integer id);
	
}



