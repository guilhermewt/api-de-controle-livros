package com.biblioteca.services.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends StandardError{
	
	private static final long serialVersionUID = 1L;
	
	private final String fields;
	private final String fieldsMessage;

}
