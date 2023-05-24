package com.biblioteca.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.biblioteca.entities.Book;
import com.biblioteca.enums.StatusBook;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.repository.RoleModelRepository;
import com.biblioteca.services.BookService;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.BookCreator;
import com.biblioteca.util.BookPostRequestBodyCreator;
import com.biblioteca.util.BookPutRequestBodyCreator;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UserDomainCreator;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {
	
	@InjectMocks
	private BookService bookService;
	
	@Mock
	private BookRepository bookRepositoryMock;
		
	@Mock
	private UserDomainRepository userDomainRepositoryMock;
	
	@Mock
	private GetUserDetails userAuthenticated;
	
	@Mock
	private RoleModelRepository roleModelRepository;
	
	@BeforeEach
	void setUp() {
		PageImpl<Book> bookPage = new PageImpl<>(List.of(BookCreator.createValidBook()));
		BDDMockito.when(bookRepositoryMock.findByUserDomainId(ArgumentMatchers.anyLong(), ArgumentMatchers.any(PageRequest.class))).thenReturn(bookPage);
		
		BDDMockito.when(bookRepositoryMock.findByUserDomainId(ArgumentMatchers.anyLong())).thenReturn(List.of(BookCreator.createValidBook()));
		
		BDDMockito.when(bookRepositoryMock.findAuthenticatedUserBooksById(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(BookCreator.createValidBook()));
		
		BDDMockito.when(bookRepositoryMock.findAuthenticatedUserBooksByTitle(ArgumentMatchers.anyString(),ArgumentMatchers.anyLong()))
		.thenReturn(List.of(BookCreator.createValidBook()));
		
		BDDMockito.when(bookRepositoryMock.save(ArgumentMatchers.any(Book.class))).thenReturn(BookCreator.createValidBook());
		
		BDDMockito.doNothing().when(bookRepositoryMock).deleteAuthenticatedUserBookById(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
		
		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		BDDMockito.when(roleModelRepository.findById(ArgumentMatchers.eq(1l))).thenReturn(Optional.of(RolesCreator.createAdminRoleModel()));
		BDDMockito.when(roleModelRepository.findById(ArgumentMatchers.eq(2l))).thenReturn(Optional.of(RolesCreator.createUserRoleModel()));
	}
	
	@Test
	@DisplayName("find all user books by id Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Book book = BookCreator.createValidBook();
		
		Page<Book> bookPage = this.bookService.findAll(PageRequest.of(0, 1));
		
		Assertions.assertThat(bookPage).isNotNull().isNotEmpty();
		Assertions.assertThat(bookPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(bookPage.toList().get(0)).isEqualTo(book);
	}
	
	@Test
	@DisplayName("find all user books by id Return List Of book whenSuccessful")
	void findAll_ReturnListOfbook_whenSuccessful() {
		Book bookSaved = BookCreator.createValidBook();
		
		List<Book> book = this.bookService.findAllNonPageable();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.get(0).getId()).isNotNull();
		Assertions.assertThat(book.get(0)).isEqualTo(bookSaved);
	}
	
	@Test
	@DisplayName("findById return book whenSuccessful")
	void findById_Returnbook_whenSuccessful() {
		Book bookSaved = BookCreator.createValidBook();
		
		Book book = this.bookService.findByIdOrElseThrowResourceNotFoundException(bookSaved.getId());
		
		Assertions.assertThat(book).isNotNull();
		Assertions.assertThat(book.getId()).isNotNull();
		Assertions.assertThat(book).isEqualTo(bookSaved);
	}
	
	@Test
	@DisplayName("findById Return Bad Request Exception When book Is Not Found")
	void findById_ReturnBadRequestExceptionWhenbookIsNotFound() {
		BDDMockito.when(bookRepositoryMock.findAuthenticatedUserBooksById(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatCode(() -> this.bookService.findByIdOrElseThrowResourceNotFoundException(1))
		.isInstanceOf(BadRequestException.class);
	}
	
	@Test
	@DisplayName("findByTitulo Return List Of book whenSuccessful")
	void findByTitulo_ReturnListOfbook_whenSuccessful() {
		Book bookSaved = BookCreator.createValidBook();
		
		List<Book> book = this.bookService.findByTitle(bookSaved.getTitle());
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.get(0).getId()).isNotNull();
		Assertions.assertThat(book.get(0)).isEqualTo(bookSaved);	
	}
	
	@Test
	@DisplayName("findByStatus Return List Of book whenSuccessful")
	void findByStatus_ReturnListOfbook_whenSuccessful() {	
		List<Book> book = this.bookService.findAllBooksByStatusNonPageable(StatusBook.LER);
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.get(0).getId()).isNotNull();
		Assertions.assertThat(book.get(0)).isEqualTo(BookCreator.createValidBook());	
	}
	
	@Test
	@DisplayName("findByStatus Return Empty List when no book is found")
	void findByStatus_ReturnEmptyListWhenNobookIsFound() {		
		List<Book> book = this.bookService.findAllBooksByStatusNonPageable(StatusBook.LENDO);
		
		Assertions.assertThat(book).isNotNull().isEmpty();
	}
	
	
	@Test
	@DisplayName("findByName Return Empty List when no book is found")
	void findByName_ReturnEmptyListWhenNobookIsFound() {
		BDDMockito.when(bookRepositoryMock.findAuthenticatedUserBooksByTitle(ArgumentMatchers.anyString(),ArgumentMatchers.anyLong()))
		.thenReturn(Collections.emptyList());
		
		List<Book> book = this.bookService.findByTitle("xaxa");
		
		Assertions.assertThat(book).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("save Return book whenSuccessful")
	void save_Returnbook_whenSuccessful() {
		BDDMockito.when(userDomainRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(UserDomainCreator.createUserDomainWithRoleADMIN()));
		
		Book bookSaved = BookCreator.createValidBook();
		
		Book book = this.bookService.save(BookPostRequestBodyCreator.createBookPostRequestBodyCreator());
		
		Assertions.assertThat(book).isNotNull();
		Assertions.assertThat(book.getId()).isNotNull();
		Assertions.assertThat(book).isEqualTo(bookSaved);
	}
	
	@Test
	@DisplayName("delete Removes book whenSuccessful")
	void delete_Removesbook_whenSuccessful() {	
		Assertions.assertThatCode(() -> this.bookService.delete(1l)).doesNotThrowAnyException();
	}
	
	@Test
	@DisplayName("update Replace book whenSuccessful")
	void update_Replavebook_whenSuccessful() {		
		BDDMockito.when(userDomainRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(UserDomainCreator.createUserDomainWithRoleADMIN()));
			
		Assertions.assertThatCode(() -> this.bookService.update(BookPutRequestBodyCreator.createBookPutRequestBodyCreator())).doesNotThrowAnyException();
	}
	
}
