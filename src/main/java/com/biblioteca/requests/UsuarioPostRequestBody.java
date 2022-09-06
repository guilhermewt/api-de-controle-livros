package com.biblioteca.requests;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Livro;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of= {"nome"})
@SuperBuilder
public class UsuarioPostRequestBody{
	
	@NotEmpty(message = "the usuario nome cannot be empty")
	private String nome;
	@NotEmpty(message = "the usuario email cannot be empty")
	private String email;
	@NotEmpty(message = "the usuario username cannot be empty")
	private String username;
	@NotEmpty(message = "the usuario password cannot be empty")
	private String password;
	@NotEmpty(message = "the usuario authorities cannot be empty")
	private String authorities;

	@OneToMany(mappedBy = "usuario")
	@Builder.Default
	private Set<Livro> livro = new HashSet<>();

	@OneToMany(mappedBy = "usuario")
	@Builder.Default
	private Set<Emprestimo> emprestimos = new HashSet<>();
	
	public UsuarioPostRequestBody( String nome, String email, String username, String password,
			String authorities) {
		super();
		this.nome = nome;
		this.email = email;
		
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
}
