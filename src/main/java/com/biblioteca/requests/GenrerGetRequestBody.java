package com.biblioteca.requests;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class GenrerGetRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@NotEmpty(message = "the book title cannot be empty")
	private String name;

    public GenrerGetRequestBody(Long id,String name) {
		super();
		this.id = id;
		this.name = name;
    }

}
