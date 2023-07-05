package io.prots.app.exception;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import io.prots.app.exception.custom.ObjectAlreadyExistsException;
import io.prots.app.exception.custom.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionMsgApiHandler {
	
	@ExceptionHandler(
		value = {
			ObjectNotFoundException.class,
			ObjectAlreadyExistsException.class,
			UsernameNotFoundException.class,
			DateTimeParseException.class,
			NumberFormatException.class,
			BadCredentialsException.class,
			RuntimeException.class
		}
	)
	public <T extends RuntimeException> ResponseEntity<ExceptionMsg> handleExceptionOutputMsg(final T e) {
		
		final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		final ExceptionMsg exceptionMsg = new ExceptionMsg(e.getMessage(), badRequest, ZonedDateTime.now());
		
		return new ResponseEntity<>(exceptionMsg, badRequest);
	}
	
	
	
}






