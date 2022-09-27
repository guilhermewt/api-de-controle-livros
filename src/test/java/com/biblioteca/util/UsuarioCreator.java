package com.biblioteca.util;

import com.biblioteca.entities.Usuario;

public class UsuarioCreator {
	
	public static Usuario createAdminUsuario() {
		return Usuario.builder()
				.id(1l)
				.name("guilhermeSilva")
				.username("guilherme")
				.email("guilherme@gmail.com")
				.password("{bcrypt}$2a$10$gUqmuiSjMuhIRW6T8IQWAOy/IcyNPj0yMyWBBd3g1HWQRa3FQkFeW")
				.authorities("ROLE_ADMIN")
				.build();
	}
	
	public static Usuario createUserUsuario() {
		return Usuario.builder()
				.id(1l)
				.name("joao")
				.username("UserBiblioteca")
				.email("joao@gmail.com")
				.password("{bcrypt}$2a$10$gUqmuiSjMuhIRW6T8IQWAOy/IcyNPj0yMyWBBd3g1HWQRa3FQkFeW")
				.authorities("ROLE_USER")
				.build();
	}
}
