package com.planepockets.proton.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<AuthenticationException> badCredentialsExceptionHandler(
			BadCredentialsException badCredentialsException) {
		return new ResponseEntity<AuthenticationException>(new AuthenticationException("Invalid Credentials"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<AuthenticationException> disabledUserExceptionHandler(
			UsernameNotFoundException usernameNotFoundException) {
		return new ResponseEntity<AuthenticationException>(new AuthenticationException("Username not found"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<AuthenticationException> disabledUserExceptionHandler(CustomException ex) {
		return new ResponseEntity<AuthenticationException>(new AuthenticationException(ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<AuthenticationException> expiredJwtExceptionHandler(ExpiredJwtException expiredJwtException) {
		return new ResponseEntity<AuthenticationException>(new AuthenticationException("This Session is expired"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<AuthenticationException> handleNullValueException(ConstraintViolationException exception) {
		Set<ConstraintViolation<?>> set = exception.getConstraintViolations();
		String errors = set.iterator().next().getMessage();
		AuthenticationException ex = new AuthenticationException(errors);
		return new ResponseEntity<AuthenticationException>(ex, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<AuthenticationException> handleMalformedTokenException(MalformedJwtException exception){
		AuthenticationException ex = new AuthenticationException("The Provided token is malformed");
		return new ResponseEntity<AuthenticationException>(ex, HttpStatus.BAD_REQUEST);
	}
}
