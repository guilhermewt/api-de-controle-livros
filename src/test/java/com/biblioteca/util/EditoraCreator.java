package com.biblioteca.util;

import com.biblioteca.entities.Editora;

public class EditoraCreator {
	
	public static Editora createValidEditora() {
		return Editora.builder()
				.id(1l)
				.nome("saraiva")
				.build();
	}
	
	public static Editora createEditoraToBeSaved() {
		return Editora.builder()
				.nome("saraiva")
				.build();
	}
	
	public static Editora createUpdatedEditora() {
		return Editora.builder()
				.id(1l)
				.nome("saraiva 2")
				.build();
	}
}
