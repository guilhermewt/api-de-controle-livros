package com.biblioteca.util;

import com.biblioteca.requests.LivroPutRequestBody;

public class LivroPutRequestBodyCreator {
	
	public static LivroPutRequestBody createLivroPutRequestBodyCreator() {
		return LivroPutRequestBody.builder()
				.id(1l)
				.titulo("o poder da acao 2")
				.anoPublicacao(DateConvert.convertData("2022/09/15"))
				.build();
	}
}
