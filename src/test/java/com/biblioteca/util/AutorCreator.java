package com.biblioteca.util;

import com.biblioteca.entities.Autor;

public class AutorCreator {
	
	public static Autor createValidAutor() {
		return Autor.builder()
				.id(1l)
				.nome("collen hoover")
				.build();
	}
	
	public static Autor createAutorToBeSaved() {
		return Autor.builder()
				.nome("collen hoover")
				.build();
	}
	
	public static Autor createUpdatedAutor() {
		return Autor.builder()
				.id(1l)
				.nome("collen hoover 2")
				.build();
	}
}
