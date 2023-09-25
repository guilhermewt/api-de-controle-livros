package com.biblioteca.util;

import com.biblioteca.enums.StatusBook;
import com.biblioteca.requests.BookPutRequestBody;

public class BookPutRequestBodyCreator {
	
	public static BookPutRequestBody createBookPutRequestBodyCreator() {
		return BookPutRequestBody.builder()
				.id(1l)
				.title("o poder da acao 2")
				.authors("author name")
				.externalCode("123")
				.genrers(GenrerCreator.createValidGenrerSet())
				.statusBook(StatusBook.LER)
				.build();
	}
}
