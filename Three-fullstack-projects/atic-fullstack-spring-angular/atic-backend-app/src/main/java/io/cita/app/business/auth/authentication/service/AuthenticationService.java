package io.cita.app.business.auth.authentication.service;

import io.cita.app.business.auth.authentication.model.LoginRequest;
import io.cita.app.business.auth.authentication.model.LoginResponse;

public interface AuthenticationService {
	
	LoginResponse authenticate(final LoginRequest loginRequest);
	
}





