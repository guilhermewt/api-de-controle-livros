package com.biblioteca.util;

import com.biblioteca.requests.EmprestimosPutRequestBody;

public class EmprestimoPutRequestBodyCreator {
	
	public static EmprestimosPutRequestBody createEmprestimoPutRequestBodyCreator() {
		return EmprestimosPutRequestBody.builder()
				.id(1l)
				.dataEmprestimo(DateConvert.convertData("2024/04/14"))
				.dataDevolucao(DateConvert.convertData("2024/04/24"))
				.build();
	}
}
