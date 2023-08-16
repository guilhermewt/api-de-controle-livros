package com.biblioteca.util;

import java.util.List;

import com.biblioteca.requests.UserDomainGetRequestBody;

public class UserDomainGetRequestBodyCreator {
	
	public static UserDomainGetRequestBody createUserGetRequestBodyCreator() {
		return UserDomainGetRequestBody.builder()
				.id(1l)
				.name("joao")
				.username("userBiblioteca")
				.email("joao@gmail.com")
				.roles(List.of(RolesCreator.createUserRoleModel()))
				.build();
	}
	
	public static UserDomainGetRequestBody createUserAdminGetRequestBodyCreator() {
		return UserDomainGetRequestBody.builder()
				.id(1l)
				.name("guilhermeSilva")
				.username("guilherme")
				.email("guilherme@gmail.com")
				.roles(List.of(RolesCreator.createAdminRoleModel()))
				.build();
	}
}
