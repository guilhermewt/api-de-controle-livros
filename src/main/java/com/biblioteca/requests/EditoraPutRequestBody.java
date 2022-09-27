package com.biblioteca.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class EditoraPutRequestBody {
	
	@Schema(description = "id to identify the book to be updated")
	private Long id;
	private String nome;
	

	public EditoraPutRequestBody(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

}
