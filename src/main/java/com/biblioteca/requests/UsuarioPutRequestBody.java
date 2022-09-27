package com.biblioteca.requests;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of= {"id","name"})
@SuperBuilder
public class UsuarioPutRequestBody{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
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
	
	@NotEmpty(message = "the usuario authorities cannot be empty")
	@Schema(description = "admin user type or common user")
	private String authorities;

	
	public UsuarioPutRequestBody(Long id, String name, String email, String username, String password,
			String authorities) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
}
