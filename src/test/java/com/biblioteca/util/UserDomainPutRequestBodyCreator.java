package com.biblioteca.util;

import com.biblioteca.requests.UserDomainPutRequestBody;

public class UserDomainPutRequestBodyCreator {
	
	public static UserDomainPutRequestBody createUserDomainPutRequestBodyCreator() {
		return UserDomainPutRequestBody.builder()
				.name("joao 2 ")
				.username("userBiblioteca 2 ")
				.email("joao2@gmail.com")
				.build();
	}
}
