package com.biblioteca.requests;

import java.util.HashSet;
import java.util.Set;

import com.biblioteca.entities.Livro;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class EditoraPutRequestBody {
	
	private Long id;
	private String nome;
	
	@Builder.Default
	private Set<Livro> livros = new HashSet<>();

	public EditoraPutRequestBody(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

}
