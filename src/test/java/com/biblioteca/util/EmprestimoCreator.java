package com.biblioteca.util;

import com.biblioteca.entities.Emprestimo;

public class EmprestimoCreator {
	
	public static Emprestimo createValidEmprestimo() {
		return Emprestimo.builder()
				.id(1l)
				.dataEmprestimo(DateConvert.convertData("2022/09/15"))
				.dataDevolucao(DateConvert.convertData("2022/09/20"))
				.usuario(UsuarioCreator.createUserUsuario())
				.build();
	}
	
	public static Emprestimo createEmprestimoToBeSaved() {
		return Emprestimo.builder()
				.dataEmprestimo(DateConvert.convertData("2022/09/15"))
				.dataDevolucao(DateConvert.convertData("2022/09/20"))
				.build();
	}
	
	public static Emprestimo createUpdatedEmprestimo() {
		return Emprestimo.builder()
				.id(1l)
				.dataEmprestimo(DateConvert.convertData("2024/04/14"))
				.dataDevolucao(DateConvert.convertData("2024/04/24"))
				.build();
	}
}
