package com.biblioteca.util;

import com.biblioteca.requests.LivroPostRequestBody;

public class LivroPostRequestBodyCreator {
	
	public static LivroPostRequestBody createLivroPostRequestBodyCreator() {
		return LivroPostRequestBody.builder()
				.titulo("o poder da acao")
				.anoPublicacao(DateConvert.convertData("2022/09/15"))
				.build();
	}
}
