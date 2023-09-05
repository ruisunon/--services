package com.demo.config;

import com.demo.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalErrorHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  public ErrorMessage handleNotFound(final HttpServletRequest request, final Exception error) {
    return ErrorMessage.from("Request not found!");
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AccessDeniedException.class)
  public ErrorMessage handleAccessDenied(final HttpServletRequest request, final Exception error) {
    return ErrorMessage.from("Forbidden. %s".formatted(error.getMessage()));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable.class)
  public ErrorMessage handleInternalError(final HttpServletRequest request, final Exception error) {
    return ErrorMessage.from(error.getMessage());
  }
}
