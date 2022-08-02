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
	
	@NotEmpty
	private String nome;
	@NotEmpty
	private String email;
	@NotEmpty
	private String login;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	private String authorities;

	@OneToMany(mappedBy = "usuario")
	@Builder.Default
	private Set<Livro> livro = new HashSet<>();

	@OneToMany(mappedBy = "usuario")
	@Builder.Default
	private Set<Emprestimo> emprestimos = new HashSet<>();
	
	public UsuarioPostRequestBody( String nome, String email, String login, String username, String password,
			String authorities) {
		super();
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
}
