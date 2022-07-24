package com.biblioteca.requests;

import java.util.HashSet;
import java.util.Set;

import com.biblioteca.entities.Livro;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutorPutRequestBody {
	
	private Long id;
	private String nome;
	
	private Set<Livro> livros = new HashSet<>();
	
	public AutorPutRequestBody(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
}
