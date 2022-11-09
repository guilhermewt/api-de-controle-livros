package com.biblioteca.requests;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of= {"id","name"})
@SuperBuilder
public class UserDomainPutRequestBody{
	@Schema(description = "id to identify the userDomain to be updated")
	private Long id;
	
	@NotEmpty(message = "the usuario name cannot be empty")
	private String name;
	
	@NotEmpty(message = "the usuario email cannot be empty")
	private String email;
	
	@NotEmpty(message = "the usuario username cannot be empty")
	@Schema(description = "the user login")
	private String username;
	
	@NotEmpty(message = "the usuario password cannot be empty")
	private String password;

	public UserDomainPutRequestBody(Long id, String name,String email, String username,String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
	}
		
}
