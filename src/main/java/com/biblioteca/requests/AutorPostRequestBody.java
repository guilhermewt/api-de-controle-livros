package com.biblioteca.requests;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class AutorPostRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "the autor name cannot be empty")
	@Schema(description = "this is the autor name", example="machado de assis")
	private String nome;

	public AutorPostRequestBody(String nome) {
		super();
		this.nome = nome;
	}
}
