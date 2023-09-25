package com.biblioteca.util;

import com.biblioteca.entities.Book;
import com.biblioteca.enums.StatusBook;

public class BookCreator {
	
	public static Book createBookWithLoan() {
		return Book.builder()
				.id(1l)
				.title("o poder da acao")
				.authors("author name")
				.externalCode("123")
				.userDomain(UserDomainCreator.createUserDomainWithRoleUSER())
				.statusBook(StatusBook.LER)
				.genrers(GenrerCreator.createValidGenrerSet())
				.loans(LoanCreator.createValidLoan())
				.build();
	}
	
	public static Book createValidBook() {
		return Book.builder()
				.id(1l)
				.title("o poder da acao")
				.authors("author name")
				.externalCode("123")
				.userDomain(UserDomainCreator.createUserDomainWithRoleUSER())
				.statusBook(StatusBook.LER)
				.genrers(GenrerCreator.createValidGenrerSet())
				.build();
	}
	
	public static Book createBookToBeSaved() {
		return Book.builder()
				.title("o poder da acao")
				.authors("author name")
				.externalCode("1234")
				.statusBook(StatusBook.LER)
				.genrers(GenrerCreator.createValidGenrerSet())
				.build();
	}
	
	public static Book createUpdatedBook() {
		return Book.builder()
				.id(1l)
				.title("o poder da acao 2")
				.authors("author name")
				.externalCode("123")
				.statusBook(StatusBook.LIDO)
				.build();
	}
}
