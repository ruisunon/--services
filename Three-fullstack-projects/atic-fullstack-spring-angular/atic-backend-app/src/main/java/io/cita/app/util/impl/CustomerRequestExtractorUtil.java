package io.cita.app.util.impl;

import io.cita.app.util.JwtUtils;
import io.cita.app.util.UserRequestExtractorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import io.cita.app.common.AppConstants;
import io.cita.app.exception.wrapper.UsernameNotMatchException;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomerRequestExtractorUtil implements UserRequestExtractorUtil {
	
	private final JwtUtils jwtUtils;
	
	@Override
	public String extractUsername(final WebRequest request) {
		
		final var usernameAuthHeader = Objects.requireNonNullElse(
				request.getHeader(AppConstants.USERNAME_AUTH_HEADER), "");
		final var authenticatedUsername = this.jwtUtils.extractUsername(Objects.requireNonNullElse(
					request.getHeader(AppConstants.AUTHORIZATION_HEADER), "")
				.substring(7)
				.strip());
		
		if (!usernameAuthHeader.isBlank())
			if (!authenticatedUsername.equalsIgnoreCase(usernameAuthHeader))
				throw new UsernameNotMatchException("Authenticated user is not allowed to access another user resources");
		
		return authenticatedUsername;
	}
	
}



