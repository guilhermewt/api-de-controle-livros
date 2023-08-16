package com.biblioteca.requests;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.biblioteca.entities.RoleModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of= {"name"})
@SuperBuilder
public class UserDomainGetRequestBody{
	
	private Long id;
	@NotEmpty(message = "the usuario name cannot be empty")
	private String name;
	
	@NotEmpty(message = "the usuario email cannot be empty")
	private String email;
	
	@NotEmpty(message = "the usuario username cannot be empty")
	@Schema(description = "the user login")
	private String username;
	
	@Builder.Default
	private List<RoleModel> roles = new ArrayList<>();
		
	public UserDomainGetRequestBody(Long id,String name, String email, String username) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;	
		this.username = username;
	}
}
