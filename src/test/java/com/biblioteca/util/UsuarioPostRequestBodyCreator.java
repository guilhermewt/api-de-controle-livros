package com.biblioteca.util;

import com.biblioteca.requests.UsuarioPostRequestBody;

public class UsuarioPostRequestBodyCreator {
	
	public static UsuarioPostRequestBody createUserPostRequestBodyCreator() {
		return UsuarioPostRequestBody.builder()
				.name("joao")
				.username("userBiblioteca")
				.email("joao@gmail.com")
				.password("$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6")
				.build();
	}
}
