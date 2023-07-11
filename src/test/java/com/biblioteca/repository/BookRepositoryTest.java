package com.biblioteca.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import com.biblioteca.entities.Book;
import com.biblioteca.util.BookCreator;
import com.biblioteca.util.GenrerCreator;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UserDomainCreator;


@DataJpaTest
@DisplayName("test for book repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookRepositoryTest {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserDomainRepository userRepository;
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	@Autowired
	private GenrerRepository genrerRepository;
	
	@BeforeEach
	public void setUp() {
		this.roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		this.userRepository.save(UserDomainCreator.createUserDomainWithRoleUSER());
		
		this.genrerRepository.saveAll(GenrerCreator.createValidGenrer());
		
		this.bookRepository.save(BookCreator.createValidBook());
	}
	
	@Test
	@DisplayName("find all user books by id and author return list of book whensuccessful")
	void findByAuthor_returnListOfbook_whenSuccessful() {
		List<Book> bookSaved = this.bookRepository.findByUserDomainIdAndAuthorsContainingIgnoreCase(
				UserDomainCreator.createUserDomainWithRoleUSER().getId()
				, "AuThor name");
		
		Assertions.assertThat(bookSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(bookSaved.get(0)).isEqualTo(BookCreator.createValidBook());
	}
	
	@Test
	@DisplayName("find all user books by id and genrer return list of book whensuccessful")
	void findByGenrer_returnListOfbook_whenSuccessful() {
		List<Book> bookSaved = this.bookRepository.findByUserDomainIdAndGenrersNameContainingIgnoreCase(
				UserDomainCreator.createUserDomainWithRoleUSER().getId()
				, GenrerCreator.createValidGenrer().get(0).getName());
		
		Assertions.assertThat(bookSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(bookSaved.get(0)).isEqualTo(BookCreator.createValidBook());
	}
	
	@Test
	@DisplayName("find all user books by id and genrer return empty list of book whensuccessful")
	void findByGenrer_returnEmptyListOfbook_whenSuccessful() {
		List<Book> bookSaved = this.bookRepository.findByUserDomainIdAndGenrersNameContainingIgnoreCase(
				UserDomainCreator.createUserDomainWithRoleUSER().getId()
				, "test");
		
		Assertions.assertThat(bookSaved).isEmpty();
		
	}
	
	
	@Test
	@DisplayName("find all user books return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Page<Book> bookSaved = this.bookRepository.findByUserDomainId(
				UserDomainCreator.createUserDomainWithRoleUSER().getId(), PageRequest.of(0, 5));
		
		Assertions.assertThat(bookSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(bookSaved.toList().get(0)).isEqualTo(BookCreator.createValidBook());
	}
	
	@Test
	@DisplayName("find all user books by id return list of book whensuccessful")
	void findAll_returnListOfbook_whenSuccessful() {
		List<Book> bookSaved = this.bookRepository.findByUserDomainId(
				UserDomainCreator.createUserDomainWithRoleUSER().getId());
		
		Assertions.assertThat(bookSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(bookSaved.get(0)).isEqualTo(BookCreator.createValidBook());
	}
	
	@Test
	@DisplayName("findById return book whenSuccessful")
	void findByid_returnbook_whenSuccessful() {
		Book bookSaved = this.bookRepository.findAuthenticatedUserBooksById(
				BookCreator.createValidBook().getId(), UserDomainCreator.createUserDomainWithRoleUSER().getId()).get();
		
		Assertions.assertThat(bookSaved).isNotNull();
		Assertions.assertThat(bookSaved.getId()).isNotNull();
		Assertions.assertThat(bookSaved).isEqualTo(BookCreator.createValidBook());
	}
	
	@Test
	@DisplayName("findAuthenticatedUserBooksByTitle Return List Of book whenSuccessful")
	void findAuthenticatedUserBooksByTitle_ReturnListOfbook_whenSuccessful() {
		String title = BookCreator.createValidBook().getTitle();
		
		List<Book> bookSaved = this.bookRepository.findAuthenticatedUserBooksByTitle(title, BookCreator.createValidBook().getUserDomain().getId());
		
		Assertions.assertThat(bookSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(bookSaved.get(0).getId()).isNotNull();
		Assertions.assertThat(bookSaved.get(0)).isEqualTo(BookCreator.createValidBook());
	}
	
	@Test
	@DisplayName("save return book whenSuccessful")
	void save_returnbook_whenSuccessful() {	
		Book bookSaved = this.bookRepository.save(BookCreator.createValidBook());
		
		Assertions.assertThat(bookSaved).isNotNull();
		Assertions.assertThat(bookSaved.getId()).isNotNull();
		Assertions.assertThat(bookSaved).isEqualTo(BookCreator.createValidBook());
	}
	
	@Test
	@DisplayName("delete removes book whenSuccessful")
	void delete_removesbook_whenSuccessful() {
		Book bookSaved = this.bookRepository.save(BookCreator.createValidBook());
		
	    this.bookRepository.deleteAuthenticatedUserBookById(bookSaved.getId(),bookSaved.getUserDomain().getId());
	    
	    Optional<Book> bookDeleted = this.bookRepository.findAuthenticatedUserBooksById(bookSaved.getId(), UserDomainCreator.createUserDomainWithRoleUSER().getId());
	    
	    Assertions.assertThat(bookDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace book whenSuccessful")
	void update_replacebook_whenSuccessful() {
	    Book bookUpdate = this.bookRepository.save(BookCreator.createUpdatedBook());
	    
	    Assertions.assertThat(bookUpdate).isNotNull();
	    Assertions.assertThat(bookUpdate.getId()).isNotNull();
	    Assertions.assertThat(bookUpdate).isEqualTo(BookCreator.createUpdatedBook());
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when book name is empty")
	void save_throwConstrationViolationException_whenbookNameIsEmpty() {
		Book book = new Book();
		
		Assertions.assertThatThrownBy(() -> this.bookRepository.save(book))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
