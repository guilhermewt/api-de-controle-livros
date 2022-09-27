package com.biblioteca.requests;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class EditoraPostRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "the editora name cannot be empty")
	@Schema(description = "this is the editora name", example = "editora saraiva,editora atena")
	private String nome;

	public EditoraPostRequestBody(String nome) {
		super();
		this.nome = nome;
	}
	
	

}
