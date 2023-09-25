package com.biblioteca.util;

import com.biblioteca.enums.StatusBook;
import com.biblioteca.requests.BookGetRequestBody;

public class BookGetRequestBodyCreator {
	
	public static BookGetRequestBody createBookGetRequestBodyCreator() {
		return BookGetRequestBody.builder()
				.id(1l)
				.title("o poder da acao")
				.authors("author name")
				.externalCode("123")
				.statusBook(StatusBook.LER)
				.genrers(GenrerCreator.createValidGenrerSet())
				.build();
	}
}
