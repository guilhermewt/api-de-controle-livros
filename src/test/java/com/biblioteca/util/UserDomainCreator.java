package com.biblioteca.util;

import java.util.List;

import com.biblioteca.entities.UserDomain;

public class UserDomainCreator {
	
	public static UserDomain createUserDomainWithRoleADMIN() {
		return UserDomain.builder()
				.id(1l)
				.name("guilhermeSilva")
				.username("guilherme")
				.email("guilherme@gmail.com")
				.password("$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6")
				.roles(List.of(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()))
				.build();
	}
	
	public static UserDomain createUserDomainWithRoleUSER() {
		return UserDomain.builder()
				.id(1l)
				.name("joao")
				.username("userBiblioteca")
				.email("joao@gmail.com")
				.password("$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6")
				.roles(List.of(RolesCreator.createUserRoleModel()))
				.build();
	}
	
	public static UserDomain createUserDomainToBeUpdate() {
		return UserDomain.builder()
				.id(1l)
				.name("joao 2 ")
				.username("userBiblioteca 2 ")
				.email("joao2@gmail.com")
				.password("$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6")
				.roles(List.of(RolesCreator.createUserRoleModel()))
				.build();
	}
}
