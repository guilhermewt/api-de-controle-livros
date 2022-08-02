package com.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.requests.EmprestimosPostRequestBody;
import com.biblioteca.requests.EmprestimosPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class EmprestimoMapper {
	
	public static EmprestimoMapper INSTANCE = Mappers.getMapper(EmprestimoMapper.class);
	
	public abstract Emprestimo toEmprestimo(EmprestimosPostRequestBody emprestimosPostRequestBody);
	
	public abstract Emprestimo toEmprestimmo(EmprestimosPutRequestBody emprestimosPutRequestBody);
}
