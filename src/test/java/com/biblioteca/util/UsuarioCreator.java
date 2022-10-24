package com.biblioteca.util;

import com.biblioteca.entities.Usuario;

public class UsuarioCreator {
	
	public static Usuario createAdminUsuario() {
		return Usuario.builder()
				.id(1l)
				.name("guilhermeSilva")
				.username("guilherme")
				.email("guilherme@gmail.com")
				.password("$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6")
				.authorities("ROLE_ADMIN")
				.build();
	}
	
	public static Usuario createUserUsuario() {
		return Usuario.builder()
				.id(1l)
				.name("joao")
				.username("userBiblioteca")
				.email("joao@gmail.com")
				.password("$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6")
				.authorities("ROLE_USER")
				.build();
	}
}
