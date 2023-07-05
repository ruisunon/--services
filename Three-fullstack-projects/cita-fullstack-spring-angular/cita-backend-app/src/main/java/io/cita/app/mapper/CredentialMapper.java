package io.cita.app.mapper;

import io.cita.app.model.domain.entity.Credential;
import io.cita.app.model.dto.CredentialDto;
import lombok.NonNull;

public interface CredentialMapper {
	
	public static CredentialDto toDto(@NonNull final Credential credential) {
		return CredentialDto.builder()
				.id(credential.getId())
				.identifier(credential.getIdentifier())
				.username(credential.getUsername())
				.password(credential.getPassword())
				.userRoleBasedAuthority(credential.getUserRoleBasedAuthority())
				.isEnabled(credential.getIsEnabled())
				.isAccountNonExpired(credential.getIsAccountNonExpired())
				.isAccountNonLocked(credential.getIsAccountNonLocked())
				.isCredentialsNonExpired(credential.getIsCredentialsNonExpired())
				.build();
	}
	
}




