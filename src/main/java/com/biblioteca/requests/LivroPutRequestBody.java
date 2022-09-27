package com.biblioteca.requests;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class LivroPutRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id to identify the book to be updated")
	private Long id;
	@NotEmpty(message = "the book title cannot be empty")
	private String titulo;
	@NonNull
	private Date anoPublicacao;
	
	public LivroPutRequestBody(Long id, String titulo, Date anoPublicacao) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.anoPublicacao = anoPublicacao;
	}
}
