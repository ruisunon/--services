package io.cita.app.service;

import io.cita.app.model.dto.VerificationTokenDto;

public interface VerificationTokenService {
	
	VerificationTokenDto findByToken(final String token);
	boolean deleteByToken(final String token);
	
}


