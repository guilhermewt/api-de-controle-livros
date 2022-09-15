package com.biblioteca.util;

import com.biblioteca.requests.EditoraPutRequestBody;

public class EditoraPutRequestBodyCreator {
	
	public static EditoraPutRequestBody createEditoraPutRequestBodyCreator() {
		return EditoraPutRequestBody.builder()
				.id(1l)
				.nome("saraiva")
				.build();
	}
}
