package com.biblioteca.util;

import com.biblioteca.entities.Livro;

public class LivroCreator {
	
	public static Livro createValidLivro() {
		return Livro.builder()
				.id(1l)
				.titulo("o poder da acao")
				.anoPublicacao(DateConvert.convertData("2022/09/15"))
				.usuario(UsuarioCreator.createUserUsuario())
				.build();
	}
	
	public static Livro createLivroToBeSaved() {
		return Livro.builder()
				.titulo("o poder da acao")
				.anoPublicacao(DateConvert.convertData("2022/09/15"))
				.build();
	}
	
	public static Livro createUpdatedLivro() {
		return Livro.builder()
				.id(1l)
				.titulo("o poder da acao 2")
				.anoPublicacao(DateConvert.convertData("2024/04/14"))
				.build();
	}
}
