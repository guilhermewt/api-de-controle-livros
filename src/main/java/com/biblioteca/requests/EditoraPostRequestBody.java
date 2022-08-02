package com.biblioteca.requests;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.biblioteca.entities.Livro;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class EditoraPostRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String nome;
	
	@Builder.Default
	private Set<Livro> livros = new HashSet<>();

	public EditoraPostRequestBody(String nome) {
		super();
		this.nome = nome;
	}
	
	@JsonIgnore
	public Set<Livro> getLivros() {
		return livros;
	}

}
