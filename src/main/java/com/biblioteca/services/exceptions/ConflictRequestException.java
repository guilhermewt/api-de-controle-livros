package com.biblioteca.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictRequestException extends RuntimeException{
	

	private static final long serialVersionUID = 1L;

	public ConflictRequestException(String msg) {
		super(msg);
	}
}
