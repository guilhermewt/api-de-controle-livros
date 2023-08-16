package com.biblioteca.util;

import com.biblioteca.requests.GenrerGetRequestBody;

public class GenrerGetRequestBodyCreator {
	
	public static GenrerGetRequestBody createGenrerGetRequestBodyCreator() {
		return GenrerGetRequestBody.builder()
				.id(1l)
				.name("Ficção científica")
				.build();
	}
}
