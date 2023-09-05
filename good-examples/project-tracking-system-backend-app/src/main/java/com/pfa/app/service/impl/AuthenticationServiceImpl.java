package com.pfa.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.pfa.app.model.dto.AuthenticationRequest;
import com.pfa.app.model.dto.AuthenticationResponse;
import com.pfa.app.service.AuthenticationService;
import com.pfa.app.service.CredentialService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final AuthenticationManager authenticationManager;
	private final CredentialService credentialService;
	
	@Autowired
	public AuthenticationServiceImpl(final AuthenticationManager authenticationManager, final CredentialService credentialService) {
		this.authenticationManager = authenticationManager;
		this.credentialService = credentialService;
	}
	
	@Override
	public AuthenticationResponse authenticate(final AuthenticationRequest authenticationRequest) throws BadCredentialsException {
		this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		return new AuthenticationResponse(authenticationRequest.getUsername(), this.credentialService.findByUsername(authenticationRequest.getUsername()).getEnabled());
	}
	
	
	
}









