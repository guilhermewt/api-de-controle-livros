package com.biblioteca.requests;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.OneToMany;

import com.biblioteca.entities.Livro;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutorPostRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String nome;
	
	@OneToMany(mappedBy = "autor")
	private Set<Livro> livros = new HashSet<>();

	public AutorPostRequestBody(String nome) {
		super();
		this.nome = nome;
	}

	@JsonIgnore
	public Set<Livro> getLivros() {
		return livros;
	}
}
