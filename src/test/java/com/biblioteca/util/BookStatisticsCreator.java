package com.biblioteca.util;

import com.biblioteca.entities.BooksStatistics;

public class BookStatisticsCreator {
	
	public static BooksStatistics createBookStatistics() {
		return BooksStatistics.builder()
				.amountBooksReading(0l)
				.numberBooksBorrowed(0l)
				.numberOfBooks(1l)
				.numberOfBooksRead(0l)
				.numberOfBooksToRead(1l)
				.build();
	}
	
}
