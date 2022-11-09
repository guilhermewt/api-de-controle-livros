package com.biblioteca.util;

import com.biblioteca.requests.BookPutRequestBody;

public class BookPutRequestBodyCreator {
	
	public static BookPutRequestBody createBookPutRequestBodyCreator() {
		return BookPutRequestBody.builder()
				.id(1l)
				.title("o poder da acao 2")
				.yearPublication(DateConvert.convertData("2022/09/15"))
				.build();
	}
}
