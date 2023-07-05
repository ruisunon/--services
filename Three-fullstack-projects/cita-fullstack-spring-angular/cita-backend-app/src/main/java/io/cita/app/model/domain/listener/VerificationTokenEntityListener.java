package io.cita.app.model.domain.listener;

import io.cita.app.model.domain.entity.VerificationToken;
import jakarta.persistence.PrePersist;

import java.util.UUID;

public class VerificationTokenEntityListener {
	
	@PrePersist
	public void preCreate(final VerificationToken verificationToken) {
		verificationToken.setToken(UUID.randomUUID().toString());
	}
	
}



