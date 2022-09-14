package com.biblioteca.util;

import com.biblioteca.requests.AutorPutRequestBody;

public class AutorPutRequestBodyCreator {
	
	public static AutorPutRequestBody createAutorPutRequestBodyCreator() {
		return AutorPutRequestBody.builder()
				.id(1l)
				.nome("jhon lenon 2")
				.build();
	}
}
