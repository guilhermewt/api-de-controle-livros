package com.biblioteca.util;

import com.biblioteca.requests.UserDomainPutRequestBody;

public class UserDomainPutRequestBodyCreator {
	
	public static UserDomainPutRequestBody createUserDomainPutRequestBodyCreator() {
		return UserDomainPutRequestBody.builder()
				.id(1l)
				.name("joao 2 ")
				.username("userBiblioteca 2 ")
				.email("joao2@gmail.com")
				.password("$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6")
				.build();
	}
}
