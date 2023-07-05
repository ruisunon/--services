package io.cita.app.mapper;

import io.cita.app.model.domain.entity.VerificationToken;
import io.cita.app.model.dto.CredentialDto;
import io.cita.app.model.dto.VerificationTokenDto;
import jakarta.validation.constraints.NotNull;

public interface VerificationTokenMapper {
	
	public static VerificationTokenDto toDto(@NotNull final VerificationToken verificationToken) {
		return VerificationTokenDto.builder()
				.id(verificationToken.getId())
				.identifier(verificationToken.getIdentifier())
				.token(verificationToken.getToken())
				.expireDate(verificationToken.getExpireDate())
				.credentialDto(
					CredentialDto.builder()
						.id(verificationToken.getCredential().getId())
						.identifier(verificationToken.getCredential().getIdentifier())
						.username(verificationToken.getCredential().getUsername())
						.password(verificationToken.getCredential().getPassword())
						.isEnabled(verificationToken.getCredential().getIsEnabled())
						.isAccountNonExpired(verificationToken.getCredential().getIsAccountNonExpired())
						.isAccountNonLocked(verificationToken.getCredential().getIsAccountNonLocked())
						.isCredentialsNonExpired(verificationToken.getCredential().getIsCredentialsNonExpired())
						.build())
				.build();
	}
	
}




