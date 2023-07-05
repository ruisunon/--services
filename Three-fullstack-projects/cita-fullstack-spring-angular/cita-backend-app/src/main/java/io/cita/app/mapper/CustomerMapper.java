package io.cita.app.mapper;

import io.cita.app.model.domain.entity.Customer;
import io.cita.app.model.domain.entity.UserImage;
import io.cita.app.model.dto.CredentialDto;
import io.cita.app.model.dto.CustomerDto;
import io.cita.app.model.dto.UserImageDto;
import lombok.NonNull;

import java.util.Objects;

public interface CustomerMapper {
	
	public static CustomerDto toDto(@NonNull final Customer customer) {
		
		final var userImage = Objects
				.requireNonNullElseGet(customer.getUserImage(), UserImage::new);
		
		return CustomerDto.builder()
				.id(customer.getId())
				.identifier(customer.getIdentifier())
				.ssn(customer.getSsn())
				.firstname(customer.getFirstname())
				.lastname(customer.getLastname())
				.isMale(customer.getIsMale())
				.email(customer.getEmail())
				.phone(customer.getPhone())
				.birthdate(customer.getBirthdate())
				.facebookUrl(customer.getFacebookUrl())
				.instagramUrl(customer.getInstagramUrl())
				.linkedinUrl(customer.getLinkedinUrl())
				.userImageDto(
					UserImageDto.builder()
						.id(userImage.getId())
						.identifier(userImage.getIdentifier())
						.imageLob(userImage.getImageLob())
						.build())
				.credentialDto(
					CredentialDto.builder()
						.id(customer.getCredential().getId())
						.identifier(customer.getCredential().getIdentifier())
						.username(customer.getCredential().getUsername())
						.password(customer.getCredential().getPassword())
						.userRoleBasedAuthority(customer.getCredential().getUserRoleBasedAuthority())
						.isEnabled(customer.getCredential().getIsEnabled())
						.isAccountNonExpired(customer.getCredential().getIsAccountNonExpired())
						.isAccountNonLocked(customer.getCredential().getIsAccountNonLocked())
						.isCredentialsNonExpired(customer.getCredential().getIsCredentialsNonExpired())
						.build())
				.build();
	}
	
}




