package com.biblioteca.resources;

import java.util.Collections;
import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.biblioteca.entities.Book;
import com.biblioteca.enums.StatusBook;
import com.biblioteca.requests.BookPostRequestBody;
import com.biblioteca.services.BookService;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.BookCreator;
import com.biblioteca.util.BookPostRequestBodyCreator;
import com.biblioteca.util.BookPutRequestBodyCreator;
import com.biblioteca.util.UserDomainCreator;

@ExtendWith(SpringExtension.class)
public class BookResourceTest {
	//test integration,repository,test
	@InjectMocks
	private BookResources bookResource;
	
	@Mock
	private BookService bookServiceMock;
	
	@Mock
	private GetUserDetails userAuthenticated;
	
	@BeforeEach
	void setUp() {
		PageImpl<Book> bookPage = new PageImpl<>(List.of(BookCreator.createValidBook()));
		BDDMockito.when(bookServiceMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(bookPage);	
		
		BDDMockito.when(bookServiceMock.findAllNonPageable())
		.thenReturn(List.of(BookCreator.createValidBook()));
		
		BDDMockito.when(bookServiceMock.findByTitle(ArgumentMatchers.anyString())).thenReturn(List.of(BookCreator.createValidBook()));
		
		BDDMockito.when(bookServiceMock.findAllBooksByStatusNonPageable(ArgumentMatchers.any())).thenReturn(List.of(BookCreator.createValidBook()));
		
		BDDMockito.when(bookServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
		.thenReturn(BookCreator.createValidBook());
		
		BDDMockito.when(bookServiceMock.save(ArgumentMatchers.any(BookPostRequestBody.class)))
		.thenReturn(BookCreator.createValidBook());
		
		BDDMockito.doNothing().when(bookServiceMock).delete(ArgumentMatchers.anyLong());
		
		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());
		
	}
	
	@Test
	@DisplayName("find all user books Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Book bookSaved = BookCreator.createValidBook();
		
		Page<Book> book = this.bookResource.findAll(PageRequest.of(0, 1)).getBody();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(book.toList().get(0)).isEqualTo(bookSaved);		
	}
	
	@Test
	@DisplayName("findAll_Return List of book whenSuccessful")
	void findAll_ReturnListOfbook_whenSuccessful() {
		Book bookSaved = BookCreator.createValidBook();
		
		List<Book> book = this.bookResource.findAllNonPageable().getBody();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.get(0).getId()).isNotNull();
		Assertions.assertThat(book.get(0)).isEqualTo(bookSaved);		
	}
	
	@Test
	@DisplayName("findByName Return List of book whenSuccessful")
	void findByName_ReturnListofbook_whenSuccessful() {
		Book bookSaved = BookCreator.createValidBook();
		
		List<Book> book = this.bookResource.findByTitle(bookSaved.getTitle()).getBody();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.get(0).getId()).isNotNull();
		Assertions.assertThat(book.get(0)).isEqualTo(bookSaved);		
	}
	
	@Test
	@DisplayName("findByName Return Empty List when No book Is Found")
	void findByName_ReturnEmptyListWhenNobookIsFound() {
		BDDMockito.when(bookServiceMock.findByTitle(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<Book> book = this.bookResource.findByTitle("fadf").getBody();
		
		Assertions.assertThat(book).isNotNull().isEmpty();		
	}
	
	@Test
	@DisplayName("findById Return book whenSuccessful")
	void findById_Returnbook_whenSuccessful() {
		Book bookSaved = BookCreator.createValidBook();
		
		Book book = this.bookResource.findById(1).getBody();
		
		Assertions.assertThat(book).isNotNull();
		Assertions.assertThat(book.getId()).isNotNull();
		Assertions.assertThat(book).isEqualTo(bookSaved);		
	}
	
	@Test
	@DisplayName("findByStatus Return List Of book whenSuccessful")
	void findByStatus_ReturnListOfbook_whenSuccessful() {	
		List<Book> book = this.bookResource.findBookByStatus("LER").getBody();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.get(0).getId()).isNotNull();
		Assertions.assertThat(book.get(0)).isEqualTo(BookCreator.createValidBook());	
	}
	
	@Test
	@DisplayName("findByStatus Return Empty List when no book is found")
	void findByStatus_ReturnEmptyListWhenNobookIsFound() {		
		BDDMockito.when(bookServiceMock.findAllBooksByStatusNonPageable(StatusBook.EMPRESTADO))
		.thenReturn(Collections.emptyList());
		List<Book> book = this.bookResource.findBookByStatus("EMPRESTADO").getBody();
		
		Assertions.assertThat(book).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("save Return book whenSuccessful")
	void save_Returnbook_whenSuccessful() {
		Book bookSaved = BookCreator.createValidBook();
		
		Book book = this.bookResource.save(BookPostRequestBodyCreator.createBookPostRequestBodyCreator()).getBody();
		
		Assertions.assertThat(book).isNotNull();
		Assertions.assertThat(book.getId()).isNotNull();
		Assertions.assertThat(book).isEqualTo(bookSaved);		
	}
	
	@Test
	@DisplayName("delete removes book whenSuccessful")
	void delete_removesbook_whenSuccessful() {		
		ResponseEntity<Void> book = this.bookResource.delete(1l);
		
		Assertions.assertThat(book.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace book whenSuccessful")
	void update_replacebook_whenSuccessful() {		
	    this.bookResource.save(BookPostRequestBodyCreator.createBookPostRequestBodyCreator()).getBody();
		
		ResponseEntity<Void> book = this.bookResource.update(BookPutRequestBodyCreator.createBookPutRequestBodyCreator());
		
		Assertions.assertThat(book.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);					
	}
	
}
