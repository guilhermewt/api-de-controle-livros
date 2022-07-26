package com.biblioteca.services.exceptions;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class StandardError implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Instant timestamp;
	private Integer status;
	private String title;
	private String details;
	private String developerMessage;
	

}
