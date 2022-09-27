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
public class LivroPostRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "the book title cannot be empty")
	private String titulo;
	
	@NonNull
	@Schema(description =  "year the book was published")
	private Date anoPublicacao;
	
	public LivroPostRequestBody(String titulo, Date anoPublicacao) {
		super();
		this.titulo = titulo;
		this.anoPublicacao = anoPublicacao;
	}
}
