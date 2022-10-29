package com.biblioteca.util;

import com.biblioteca.requests.UsuarioPutRequestBody;

public class UsuarioPutRequestBodyCreator {
	
	public static UsuarioPutRequestBody createUserPutRequestBodyCreator() {
		return UsuarioPutRequestBody.builder()
				.name("joao 2 ")
				.username("userBiblioteca 2 ")
				.email("joao2@gmail.com")
				.password("$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6")
				.build();
	}
}
