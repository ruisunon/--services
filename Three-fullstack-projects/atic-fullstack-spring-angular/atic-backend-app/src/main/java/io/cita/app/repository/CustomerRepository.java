package io.cita.app.repository;

import io.cita.app.model.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	Optional<Customer> findByIdentifier(final String identifier);
	Optional<Customer> findByCredentialUsernameIgnoringCase(final String username);
	List<Customer> findAllBySsn(final String ssn);
	
}



