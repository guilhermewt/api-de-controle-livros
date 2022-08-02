package com.biblioteca.requests;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.lang.NonNull;

import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class EmprestimosPostRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NonNull
	private Date dataEmprestimo;
	
	@NonNull
	private Date dataDevolucao;
		
	private Usuario usuario;
    
	@Builder.Default
	private Set<Livro> livros = new HashSet<>();
	
	public EmprestimosPostRequestBody(Date dataEmprestimo, Date dataDevolucao) {
		super();
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
	}

}
