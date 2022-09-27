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
public class EmprestimosPostRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NonNull
	@Schema(description = "start of the loan")
	private Date dataEmprestimo;
	
	@NonNull
	@Schema(description = "end of the loan")
	private Date dataDevolucao;
	
	
	public EmprestimosPostRequestBody(Date dataEmprestimo, Date dataDevolucao) {
		super();
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
	}

}
