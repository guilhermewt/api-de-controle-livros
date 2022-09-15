package com.biblioteca.util;

import com.biblioteca.requests.EditoraPostRequestBody;

public class EditoraPostRequestBodyCreator {
	
	public static EditoraPostRequestBody createEditoraPostRequestBodyCreator() {
		return EditoraPostRequestBody.builder()
				.nome("saraiva")
				.build();
	}
}
