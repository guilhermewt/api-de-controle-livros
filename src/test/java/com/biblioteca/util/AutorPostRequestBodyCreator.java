package com.biblioteca.util;

import com.biblioteca.requests.AutorPostRequestBody;

public class AutorPostRequestBodyCreator {
	
	public static AutorPostRequestBody createAutorPostRequestBodyCreator() {
		return AutorPostRequestBody.builder()
				.nome("jhon lenon")
				.build();
	}
}
