package com.biblioteca.requests;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.biblioteca.entities.Autor;
import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Usuario;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class LivroPutRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String titulo;
	private Date anoPublicacao;
	
	private Usuario usuario;
	
	@Builder.Default
	private Set<Emprestimo> emprestimos = new HashSet<>();
	
	private Editora editora;

	private Autor autor;
	
	public LivroPutRequestBody(Long id, String titulo, Date anoPublicacao) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.anoPublicacao = anoPublicacao;
	}
}
