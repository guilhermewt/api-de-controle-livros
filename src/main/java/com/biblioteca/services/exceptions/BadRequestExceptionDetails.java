package com.biblioteca.services.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BadRequestExceptionDetails extends StandardError {

	private static final long serialVersionUID = 1L;

}
