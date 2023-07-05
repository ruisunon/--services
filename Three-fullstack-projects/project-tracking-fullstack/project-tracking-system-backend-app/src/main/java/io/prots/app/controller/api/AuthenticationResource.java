package io.prots.app.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.prots.app.model.dto.AuthenticationRequest;
import io.prots.app.model.dto.AuthenticationResponse;
import io.prots.app.service.AuthenticationService;

@RestController
@RequestMapping(value = {"/api/authenticate"})
public class AuthenticationResource {
	
	private final AuthenticationService authenticationService;
	
	//@Autowired
	public AuthenticationResource(final AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	@PostMapping(value = {"", "/"})
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody final AuthenticationRequest authenticationRequest) {
		return ResponseEntity.ok(this.authenticationService.authenticate(authenticationRequest));
	}
}







