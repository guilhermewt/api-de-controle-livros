package com.biblioteca.resources.exceptions;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.exceptions.BadRequestExceptionDetails;
import com.biblioteca.services.exceptions.ValidationExceptionDetails;

@ControllerAdvice
public class ResourceExceptionHandler {
		
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
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){

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
	
}
