package com.biblioteca.requests;

import java.io.Serializable;
import java.util.Date;

import org.springframework.lang.NonNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class EmprestimosPutRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id to identify the book to be updated")
	private Long id;
	@NonNull
	private Date dataEmprestimo;
	@NonNull
	private Date dataDevolucao;
		
	
	public EmprestimosPutRequestBody(Long id, Date dataEmprestimo, Date dataDevolucao) {
		super();
		this.id = id;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
	}

}
