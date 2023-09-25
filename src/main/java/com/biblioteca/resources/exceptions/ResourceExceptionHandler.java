package com.biblioteca.resources.exceptions;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.exceptions.BadRequestExceptionDetails;
import com.biblioteca.services.exceptions.ConflictRequestException;
import com.biblioteca.services.exceptions.StandardError;
import com.biblioteca.services.exceptions.ValidationExceptionDetails;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler{
		
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> resourceNotFound(BadRequestException bre){
		return new ResponseEntity<>(BadRequestExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("bad Request Exception, check the documentation")
				.details(bre.getMessage())
				.developerMessage(bre.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		
				return new ResponseEntity<>(ValidationExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("bad Request Exception, invalid fields")
				.details(exception.getMessage())
				.developerMessage(exception.getClass().getName())
				.fields(fields)
				.fieldsMessage(fieldsMessage)
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		StandardError standardError = StandardError.builder()
				.timestamp(Instant.now())
				.status(status.value())
				.title(ex.getCause().getMessage())
				.details(ex.getMessage())
				.developerMessage(ex.getClass().getName())
				.build();
		
		return new ResponseEntity<>(standardError, headers, status);
	}
	
	//DataIntegrityViolationException
//	@ExceptionHandler(DataIntegrityViolationException.class)
//	public ResponseEntity<BadRequestExceptionDetails> resourceNotFound(DataIntegrityViolationException bre){
//		return new ResponseEntity<>(BadRequestExceptionDetails.builder()
//				.timestamp(Instant.now())
//				.status(HttpStatus.CONFLICT.value())
//				.title("bad Request Exception, check the documentation")
//				.details(bre.getMessage())
//				.developerMessage(bre.getClass().getName())
//				.build(), HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(ConflictRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> conflictRequestException(ConflictRequestException bre){
		return new ResponseEntity<>(BadRequestExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(HttpStatus.CONFLICT.value())
				.title("Conflict Exception, check the documentation")
				.details(bre.getMessage())
				.developerMessage(bre.getClass().getName())
				.build(), HttpStatus.CONFLICT);
	}
	
}
