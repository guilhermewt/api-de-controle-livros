package com.biblioteca.requests;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of= {"name","username"})
@SuperBuilder
public class UserDomainPutRequestBody{
	@NotEmpty(message = "the usuario name cannot be empty")
	private String name;
	
	@NotEmpty(message = "the usuario email cannot be empty")
	private String email;
	
	@NotEmpty(message = "the usuario username cannot be empty")
	@Schema(description = "the user login")
	private String username;

	public UserDomainPutRequestBody(String name,String email, String username) {
		super();
		this.name = name;
		this.email = email;
		this.username = username;
	}
		
}
