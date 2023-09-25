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
import com.biblioteca.entities.BooksStatistics;
import com.biblioteca.enums.StatusBook;
import com.biblioteca.requests.BookGetRequestBody;
import com.biblioteca.services.BookService;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.BookCreator;
import com.biblioteca.util.BookGetRequestBodyCreator;
import com.biblioteca.util.BookPostRequestBodyCreator;
import com.biblioteca.util.BookPutRequestBodyCreator;
import com.biblioteca.util.BookStatisticsCreator;
import com.biblioteca.util.UserDomainCreator;

@ExtendWith(SpringExtension.class)
public class BookResourceTest {

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
		
		BDDMockito.when(bookServiceMock.findByTitle(ArgumentMatchers.anyString(),ArgumentMatchers.any(PageRequest.class))).thenReturn(bookPage);
		
		BDDMockito.when(bookServiceMock.findByAuthors(ArgumentMatchers.anyString(),ArgumentMatchers.any(PageRequest.class))).thenReturn(bookPage);

		BDDMockito.when(bookServiceMock.findAllBooksByStatus(ArgumentMatchers.any(),ArgumentMatchers.any(PageRequest.class))).thenReturn(bookPage);
		
		BDDMockito.when(bookServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
		.thenReturn(BookCreator.createValidBook());
		
		BDDMockito.when(bookServiceMock.save(ArgumentMatchers.any(Book.class)))
		.thenReturn(BookCreator.createValidBook());
		
		BDDMockito.doNothing().when(bookServiceMock).delete(ArgumentMatchers.anyLong());
		
		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		BDDMockito.when(bookServiceMock.findByGenrer(ArgumentMatchers.anyString(),ArgumentMatchers.any(PageRequest.class))).thenReturn(bookPage);
		
		BDDMockito.when(bookServiceMock.getBooksStatistics()).thenReturn(BookStatisticsCreator.createBookStatistics());
		
	}
	
	@Test
	@DisplayName("get statistics return statistics about books whenSuccessful")
	void getStatistics_ReturnStatisticsAboudBooks_whenSuccessful() {
		BooksStatistics statistics = this.bookResource.getBooksStatistics().getBody();
		
		Assertions.assertThat(statistics).isNotNull();
		Assertions.assertThat(statistics).isNotNull();
		Assertions.assertThat(statistics).isEqualTo(BookStatisticsCreator.createBookStatistics());
	}
	
	
	@Test
	@DisplayName("find all user books Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {

		Page<BookGetRequestBody> book = this.bookResource.findAll(PageRequest.of(0, 1)).getBody();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(book.toList().get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());		
	}
	
	@Test
	@DisplayName("findAll_Return List of book whenSuccessful")
	void findAll_ReturnListOfbook_whenSuccessful() {

		List<BookGetRequestBody> book = this.bookResource.findAllNonPageable().getBody();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.get(0).getId()).isNotNull();
		Assertions.assertThat(book.get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());		
	}
	
	@Test
	@DisplayName("findByTitle Return List Of Object Inside Page whenSuccessful")
	void findBytitle_ReturnListOfObjectInsidePage_whenSuccessful() {
		Page<BookGetRequestBody> book = this.bookResource.findByTitle(BookGetRequestBodyCreator.createBookGetRequestBodyCreator().getTitle(),PageRequest.of(0, 1)).getBody();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(book.toList().get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());		
	}
	
	@Test
	@DisplayName("findByTitle Return Empty List when No book Is Found")
	void findByTitle_ReturnEmptyListWhenNobookIsFound() {
		BDDMockito.when(bookServiceMock.findByTitle(ArgumentMatchers.anyString(),ArgumentMatchers.any(PageRequest.class)))
		.thenReturn(new PageImpl<>(Collections.emptyList()));
		
		Page<BookGetRequestBody> book = this.bookResource.findByTitle("fadf",PageRequest.of(0,1)).getBody();
		
		Assertions.assertThat(book).isNotNull().isEmpty();		
	}
	
	@Test
	@DisplayName("findById Return book whenSuccessful")
	void findById_Returnbook_whenSuccessful() {
		BookGetRequestBody book = this.bookResource.findById(1l).getBody();
		
		Assertions.assertThat(book).isNotNull();
		Assertions.assertThat(book.getId()).isNotNull();
		Assertions.assertThat(book).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());		
	}
	
	@Test
	@DisplayName("findByStatus Return List Of Object Inside Page whenSuccessful")
	void findByStatus_ReturnListOfObjectInsidePage_whenSuccessful() {	
		Page<BookGetRequestBody> book = this.bookResource.findBookByStatus("LER",PageRequest.of(0, 1)).getBody();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(book.toList().get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());	
	}
	
	@Test
	@DisplayName("findByStatus Return Empty List when no book is found")
	void findByStatus_ReturnEmptyListWhenNobookIsFound() {		
		BDDMockito.when(bookServiceMock.findAllBooksByStatus(StatusBook.EMPRESTADO,PageRequest.of(0,1)))
		.thenReturn(new PageImpl<>(Collections.emptyList()));
		
		Page<BookGetRequestBody> book = this.bookResource.findBookByStatus("EMPRESTADO",PageRequest.of(0,1)).getBody();
		
		Assertions.assertThat(book).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("findByAuthor Return List Of Object Inside Page whenSuccessful")
	void findByAuthor_ReturnListofbook_whenSuccessful() {
		Book bookSaved = BookCreator.createValidBook();
		
		Page<BookGetRequestBody> book = this.bookResource.findByTitle(bookSaved.getAuthors(),PageRequest.of(0, 1)).getBody();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(book.toList().get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());		
	}
	
	@Test
	@DisplayName("findByAuthor Return Empty List when No book Is Found")
	void findByAuthor_ReturnEmptyListWhenNobookIsFound() {
		BDDMockito.when(bookServiceMock.findByAuthors(ArgumentMatchers.anyString(),ArgumentMatchers.any(PageRequest.class)))
				.thenReturn(new PageImpl<>(Collections.emptyList()));
		
		Page<BookGetRequestBody> book = this.bookResource.findByAuthor("test",PageRequest.of(0, 1)).getBody();
		
		Assertions.assertThat(book).isNotNull().isEmpty();		
	}
	
	@Test
	@DisplayName("findByGenrer Return List Of Object Inside Page whenSuccessful")
	void findByGenrer_ReturnListOfObjectInsidePage_whenSuccessful() {
		Book bookSaved = BookCreator.createValidBook();
		
		Page<BookGetRequestBody> book = this.bookResource.findByGenrer("Ficção científica",PageRequest.of(0, 1)).getBody();
		
		Assertions.assertThat(book).isNotNull().isNotEmpty();
		Assertions.assertThat(book.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(book.toList().get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());		
	}
	
	@Test
	@DisplayName("findByGenrer Return Empty List when No book Is Found")
	void findByGenrer_ReturnEmptyListWhenNobookIsFound() {
		BDDMockito.when(bookServiceMock.findByGenrer(ArgumentMatchers.anyString(),ArgumentMatchers.any(PageRequest.class)))
		.thenReturn(new PageImpl<>(Collections.emptyList()));
		
		Page<BookGetRequestBody> book = this.bookResource.findByGenrer("test",PageRequest.of(0, 1)).getBody();
		
		Assertions.assertThat(book).isNotNull().isEmpty();		
	}
	
	@Test
	@DisplayName("save Return book whenSuccessful")
	void save_Returnbook_whenSuccessful() {
		BookGetRequestBody book = this.bookResource.save(BookPostRequestBodyCreator.createBookPostRequestBodyCreator()).getBody();
		
		Assertions.assertThat(book).isNotNull();
		Assertions.assertThat(book.getId()).isNotNull();
		Assertions.assertThat(book).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());		
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
