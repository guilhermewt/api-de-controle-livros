package com.biblioteca.services.exceptions;

import java.io.Serializable;
import java.time.Instant;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BadRequestExceptionDetails extends StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	private Instant timestamp;
	private Integer status;
	private String title;
	private String details;
	private String developerMessage;

}
