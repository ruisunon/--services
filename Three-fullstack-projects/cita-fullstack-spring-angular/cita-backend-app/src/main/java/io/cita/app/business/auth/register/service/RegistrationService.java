package io.cita.app.business.auth.register.service;

import io.cita.app.business.auth.register.model.RegisterRequest;
import io.cita.app.business.auth.register.model.RegisterResponse;

public interface RegistrationService {
	
	RegisterResponse register(final RegisterRequest registerRequest);
	String validateToken(final String token);
	RegisterResponse resendToken(final String username);
	
}





