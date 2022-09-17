package com.biblioteca.util;

import com.biblioteca.requests.EmprestimosPostRequestBody;

public class EmprestimoPostRequestBodyCreator {
	
	public static EmprestimosPostRequestBody createEmprestimoPostRequestBodyCreator() {
		return EmprestimosPostRequestBody.builder()
				.dataEmprestimo(DateConvert.convertData("2022/09/15"))
				.dataDevolucao(DateConvert.convertData("2022/09/20"))
				.build();
	}
}
