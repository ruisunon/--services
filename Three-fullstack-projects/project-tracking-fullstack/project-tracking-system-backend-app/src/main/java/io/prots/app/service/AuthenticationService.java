package io.prots.app.service;

import io.prots.app.model.dto.AuthenticationRequest;
import io.prots.app.model.dto.AuthenticationResponse;

public interface AuthenticationService {
	
	AuthenticationResponse authenticate(final AuthenticationRequest authenticationRequest);
	
}





