package com.biblioteca.util;

import com.biblioteca.enums.StatusBook;
import com.biblioteca.requests.BookPostRequestBody;

public class BookPostRequestBodyCreator {
	
	public static BookPostRequestBody createBookPostRequestBodyCreator() {
		return BookPostRequestBody.builder()
				.title("o poder da acao")
				.authors("author name")
				.externalCode("123")
				.genrers(GenrerCreator.createValidGenrerSet())
				.statusBook(StatusBook.LER)
				.build();
	}
}
