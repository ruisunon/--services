package io.cita.app.business.auth.register.resource;

import io.cita.app.business.auth.register.model.RegisterRequest;
import io.cita.app.business.auth.register.model.RegisterResponse;
import io.cita.app.business.auth.register.service.RegistrationService;
import io.cita.app.model.dto.response.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.api-version}" + "/register")
@Slf4j
@RequiredArgsConstructor
public class RegistrationResource {
	
	private final RegistrationService registrationService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody @Valid final RegisterRequest registerRequest) {
		log.info("** Register user.. *");
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true,
				this.registrationService.register(registerRequest)));
	}
	
	@GetMapping("/{token}")
	public ResponseEntity<ApiResponse<String>> validateToken(@PathVariable final String token) {
		log.info("** Validate token for register user.. *");
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true,
				this.registrationService.validateToken(token)));
	}
	
	@GetMapping("/resend")
	public ResponseEntity<ApiResponse<RegisterResponse>> resendToken(@RequestParam final String username) {
		log.info("** Resend token for account validation.. *");
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true,
				this.registrationService.resendToken(username)));
	}
	
}




