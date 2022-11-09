package com.biblioteca.util;

import com.biblioteca.requests.BookPostRequestBody;

public class BookPostRequestBodyCreator {
	
	public static BookPostRequestBody createBookPostRequestBodyCreator() {
		return BookPostRequestBody.builder()
				.title("o poder da acao")
				.yearPublication(DateConvert.convertData("2022/09/15"))
				.build();
	}
}
