package com.biblioteca.integration;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.biblioteca.data.JwtObject;
import com.biblioteca.entities.Book;
import com.biblioteca.entities.RoleModel;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.repository.RoleModelRepository;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.requests.BookGetRequestBody;
import com.biblioteca.requests.BookPostRequestBody;
import com.biblioteca.requests.LoginGetRequestBody;
import com.biblioteca.util.BookCreator;
import com.biblioteca.util.BookGetRequestBodyCreator;
import com.biblioteca.util.BookPostRequestBodyCreator;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UserDomainCreator;
import com.biblioteca.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class BookResourceIT {

	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserDomainRepository userDomainRepository;
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	private static RoleModel RoleADMIN = RolesCreator.createAdminRoleModel();
	private static RoleModel RoleUSER = RolesCreator.createUserRoleModel();
	
	private static UserDomain USER = UserDomainCreator.createUserDomainWithRoleUSER();
	
	@TestConfiguration
	@Lazy
	static class Config {
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdmin(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}
		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateRoleUser(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	public HttpHeaders httpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();		
		httpHeaders.add("Authorization", "Bearer " + login().getToken());		
		return httpHeaders;
	}
	
	public JwtObject login() {
		LoginGetRequestBody login = new LoginGetRequestBody("userBiblioteca","biblioteca");
		ResponseEntity<JwtObject> jwt = testRestTemplateRoleUser.postForEntity("/login", login, JwtObject.class);
		return jwt.getBody();
	}
	
	@Test
	@DisplayName("findAll Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
	    bookRepository.save(BookCreator.createValidBook());
		
		PageableResponse<BookGetRequestBody> BookPage = testRestTemplateRoleUser.exchange("/books", HttpMethod.GET, new HttpEntity<>(httpHeaders()), 
				new ParameterizedTypeReference<PageableResponse<BookGetRequestBody>>() {
		}).getBody();
		
		Assertions.assertThat(BookPage).isNotNull().isNotEmpty();
		Assertions.assertThat(BookPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(BookPage.toList().get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());		
	}
	
	@Test
	@DisplayName("findAll Return List Of Book whenSuccessful")
	void findAll_ReturnListBook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		bookRepository.save(BookCreator.createValidBook());

		
		List<BookGetRequestBody> Book = testRestTemplateRoleUser.exchange("/books/all", HttpMethod.GET, new HttpEntity<>(httpHeaders()), 
				 new ParameterizedTypeReference<List<BookGetRequestBody>>() {
				}).getBody();
		
		Assertions.assertThat(Book).isNotNull().isNotEmpty();
		Assertions.assertThat(Book.get(0).getId()).isNotNull();
		Assertions.assertThat(Book.get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());
	}
	
	@Test
	@DisplayName("findByTitle Return List Of Object Inside Page whenSuccessful")
	void findByTitle_ReturnListOfObjectInsidePage_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		bookRepository.save(BookCreator.createValidBook());
			
		String url = String.format("/books/find-by-title?title=%s",BookGetRequestBodyCreator.createBookGetRequestBodyCreator().getTitle());
		
		PageableResponse<BookGetRequestBody> Book = testRestTemplateRoleUser.exchange(url, HttpMethod.GET,new HttpEntity<>(httpHeaders()),
				new ParameterizedTypeReference<PageableResponse<BookGetRequestBody>>() {
				}).getBody();		
		
		Assertions.assertThat(Book).isNotNull().isNotEmpty();
		Assertions.assertThat(Book.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(Book.toList().get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());
	}
	
	@Test
	@DisplayName("findByName Return Empty List Of Book whenSuccessful")
	void findByName_ReturnEmptyListOfBook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		
		PageableResponse<BookGetRequestBody> Book = testRestTemplateRoleUser.exchange("/books/find-by-title?title=test", HttpMethod.GET,new HttpEntity<>(httpHeaders()),
				new ParameterizedTypeReference<PageableResponse<BookGetRequestBody>>() {
				}).getBody();		
		
		Assertions.assertThat(Book).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("findByAuthor Return List Of Object Inside Page whenSuccessful")
	void findByAuthor_ReturnListOfBook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		bookRepository.save(BookCreator.createValidBook());
			
		String url = String.format("/books/find-by-author?author=%s",BookGetRequestBodyCreator.createBookGetRequestBodyCreator().getAuthors());
		
		PageableResponse<BookGetRequestBody> Book = testRestTemplateRoleUser.exchange(url, HttpMethod.GET,new HttpEntity<>(httpHeaders()),
				new ParameterizedTypeReference<PageableResponse<BookGetRequestBody>>() {
				}).getBody();		
		
		Assertions.assertThat(Book).isNotNull().isNotEmpty();
		Assertions.assertThat(Book.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(Book.toList().get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());
	}
	
	@Test
	@DisplayName("findByAuthor Return Empty List Of Book whenSuccessful")
	void findByAuthor_ReturnEmptyListOfBook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		
		PageableResponse<BookGetRequestBody> Book = testRestTemplateRoleUser.exchange("/books/find-by-author?author=test", HttpMethod.GET,new HttpEntity<>(httpHeaders()),
				new ParameterizedTypeReference<PageableResponse<BookGetRequestBody>>() {
				}).getBody();		
		
		Assertions.assertThat(Book).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("findByStatus Return List Of Object Inside Page whenSuccessful")
	void findByStatus_ReturnListOfObjectInsidePage_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		 bookRepository.save(BookCreator.createValidBook());
			
		String url = String.format("/books/find-by-Status?statusBook=%s",BookGetRequestBodyCreator.createBookGetRequestBodyCreator().getStatusBook());
		
		PageableResponse<BookGetRequestBody> Book = testRestTemplateRoleUser.exchange(url, HttpMethod.GET,new HttpEntity<>(httpHeaders()),
				new ParameterizedTypeReference<PageableResponse<BookGetRequestBody>>() {
				}).getBody();		
		
		Assertions.assertThat(Book).isNotNull().isNotEmpty();
		Assertions.assertThat(Book.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(Book.toList().get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());
	}
	
	@Test
	@DisplayName("findByGenrer Return List Of Object Inside Page whenSuccessful")
	void findByGenrer_ReturnListOfBook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
	     bookRepository.save(BookCreator.createValidBook());
			
		String url = String.format("/books/find-by-genrer?genrer=%s",BookGetRequestBodyCreator.createBookGetRequestBodyCreator().getGenrers().get(0).getName());
		
		PageableResponse<BookGetRequestBody> Book = testRestTemplateRoleUser.exchange(url, HttpMethod.GET,new HttpEntity<>(httpHeaders()),
				new ParameterizedTypeReference<PageableResponse<BookGetRequestBody>>() {
				}).getBody();		
		
		Assertions.assertThat(Book).isNotNull().isNotEmpty();
		Assertions.assertThat(Book.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(Book.toList().get(0)).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());
	}
	
	@Test
	@DisplayName("findByGenrer Return Empty List Of Book whenSuccessful")
	void findByGenrer_ReturnEmptyListOfBook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		
		PageableResponse<BookGetRequestBody> Book = testRestTemplateRoleUser.exchange("/books/find-by-genrer?genrer=test", HttpMethod.GET,new HttpEntity<>(httpHeaders()),
				new ParameterizedTypeReference<PageableResponse<BookGetRequestBody>>() {
				}).getBody();		
		
		Assertions.assertThat(Book).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("findById Return Book whenSuccessful")
	void findById_ReturnBook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
        bookRepository.save(BookCreator.createValidBook());
			
		BookGetRequestBody Book = testRestTemplateRoleUser.exchange("/books/{id}", HttpMethod.GET
				,new HttpEntity<>(httpHeaders()),BookGetRequestBody.class, BookGetRequestBodyCreator.createBookGetRequestBodyCreator().getId()).getBody();
		
		Assertions.assertThat(Book).isNotNull();
		Assertions.assertThat(Book.getId()).isNotNull();
		Assertions.assertThat(Book).isEqualTo(BookGetRequestBodyCreator.createBookGetRequestBodyCreator());
	}
	
	@Test
	@DisplayName("save Return Book whenSuccessful")
	void save_ReturnBook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);

		BookPostRequestBody BookPostRequestBody = BookPostRequestBodyCreator.createBookPostRequestBodyCreator();
			
		ResponseEntity<Book> entityBook = testRestTemplateRoleUser.exchange("/books", HttpMethod.POST
				,new HttpEntity<>(BookPostRequestBody,httpHeaders()), Book.class);
		
		Assertions.assertThat(entityBook).isNotNull();
		Assertions.assertThat(entityBook.getBody()).isNotNull();
		Assertions.assertThat(entityBook.getBody().getId()).isNotNull();
		Assertions.assertThat(entityBook.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes Book whenSuccessful")
	void delete_RemovesBook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		
		Book BookSaved = bookRepository.save(BookCreator.createValidBook());
			
		ResponseEntity<Void> entityBook = testRestTemplateRoleUser.exchange("/books/{id}", HttpMethod.DELETE
				, new HttpEntity<>(httpHeaders()), Void.class,BookSaved.getId());	
		
		Assertions.assertThat(entityBook).isNotNull();
		Assertions.assertThat(entityBook.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace Book whenSuccessful")
	void update_ReplaceBook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		
		Book BookSaved = bookRepository.save(BookCreator.createValidBook());
		BookSaved.setTitle("gabriel dias");
			
		ResponseEntity<Void> entityBook = testRestTemplateRoleUser.exchange("/books", HttpMethod.PUT, 
				new HttpEntity<>(BookSaved,httpHeaders()), Void.class);
		
		Assertions.assertThat(entityBook).isNotNull();
		Assertions.assertThat(entityBook.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
}
