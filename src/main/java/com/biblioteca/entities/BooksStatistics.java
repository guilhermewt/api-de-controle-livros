package com.biblioteca.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksStatistics implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long  numberOfBooks;
	private Long numberOfBooksToRead;
	private Long numberOfBooksRead;
	private Long amountBooksReading;
	private Long numberBooksBorrowed;

}
