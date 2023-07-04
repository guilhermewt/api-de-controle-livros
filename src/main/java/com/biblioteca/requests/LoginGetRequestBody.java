package com.biblioteca.requests;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class LoginGetRequestBody {
	@NotEmpty(message = "the username cannot be empty")
	private String username;
	@NotEmpty(message = "the password cannot be empty")
	private String password;
	
}
