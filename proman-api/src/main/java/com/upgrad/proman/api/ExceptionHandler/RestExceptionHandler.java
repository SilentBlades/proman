package com.upgrad.proman.api.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.upgrad.proman.api.model.ErrorResponse;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import com.upgrad.proman.service.exception.UnauthorizedException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException exec, WebRequest request) {
		return new ResponseEntity<ErrorResponse>(
				new ErrorResponse().code(exec.getCode()).message(exec.getErrorMessage()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorResponse> unauthorizedException(UnauthorizedException exec, WebRequest request) {
		return new ResponseEntity<ErrorResponse>(
				new ErrorResponse().code(exec.getCode()).message(exec.getErrorMessage()), HttpStatus.NOT_FOUND);
	}
}
