package com.biblioteca.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import com.biblioteca.entities.Book;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.util.BookCreator;
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
	
	@Test
	@DisplayName("find all user books return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomain = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		Book bookToBeSaved = this.bookRepository.save(BookCreator.createValidBook());
		Page<Book> bookSaved = this.bookRepository.findByUserDomainId(userDomain.getId(), PageRequest.of(0, 5));
		
		Assertions.assertThat(bookSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(bookSaved.toList().get(0)).isEqualTo(bookToBeSaved);
	}
	
	@Test
	@DisplayName("find all user books by id return list of book whensuccessful")
	void findAll_returnListOfbook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomain = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		Book bookToBeSaved = this.bookRepository.save(BookCreator.createValidBook());
		List<Book> bookSaved = this.bookRepository.findByUserDomainId(userDomain.getId());
		
		Assertions.assertThat(bookSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(bookSaved.get(0)).isEqualTo(bookToBeSaved);
	}
	
	@Test
	@DisplayName("findById return book whenSuccessful")
	void findByid_returnbook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomain = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		Book bookToBeSaved = this.bookRepository.save(BookCreator.createValidBook());
		Book bookSaved = this.bookRepository.findAuthenticatedUserBooksById(bookToBeSaved.getId(), userDomain.getId()).get();
		
		Assertions.assertThat(bookSaved).isNotNull();
		Assertions.assertThat(bookSaved.getId()).isNotNull();
		Assertions.assertThat(bookSaved).isEqualTo(bookToBeSaved);
	}
	
	@Test
	@DisplayName("findAuthenticatedUserBooksByTitle Return List Of book whenSuccessful")
	void findAuthenticatedUserBooksByTitle_ReturnListOfbook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
	    this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		Book bookToBeSaved = this.bookRepository.save(BookCreator.createValidBook());
		
		String titulo = bookToBeSaved.getTitle();
		
		List<Book> bookSaved = this.bookRepository.findAuthenticatedUserBooksByTitle(titulo, BookCreator.createValidBook().getUserDomain().getId());
		
		Assertions.assertThat(bookSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(bookSaved.get(0).getId()).isNotNull();
		Assertions.assertThat(bookSaved.get(0)).isEqualTo(bookToBeSaved);
	}
	
	@Test
	@DisplayName("save return book whenSuccessful")
	void save_returnbook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		Book bookToBeSaved = BookCreator.createValidBook();
		Book bookSaved = this.bookRepository.save(bookToBeSaved);
		
		Assertions.assertThat(bookSaved).isNotNull();
		Assertions.assertThat(bookSaved.getId()).isNotNull();
		Assertions.assertThat(bookSaved).isEqualTo(bookSaved);
	}
	
	@Test
	@DisplayName("delete removes book whenSuccessful")
	void delete_removesbook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomain = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		Book bookSaved = this.bookRepository.save(BookCreator.createValidBook());
		
	    this.bookRepository.deleteAuthenticatedUserBookById(bookSaved.getId(),bookSaved.getUserDomain().getId());
	    
	    Optional<Book> bookDeleted = this.bookRepository.findAuthenticatedUserBooksById(bookSaved.getId(), userDomain.getId());
	    
	    Assertions.assertThat(bookDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace book whenSuccessful")
	void update_replacebook_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
	    this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		this.bookRepository.save(BookCreator.createValidBook());
		
		Book bookToBeUpdate = BookCreator.createUpdatedBook();
		
	    Book bookUpdate = this.bookRepository.save(bookToBeUpdate);
	    
	    Assertions.assertThat(bookUpdate).isNotNull();
	    Assertions.assertThat(bookUpdate.getId()).isNotNull();
	    Assertions.assertThat(bookUpdate).isEqualTo(bookToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when book name is empty")
	void save_throwConstrationViolationException_whenbookNameIsEmpty() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		Book book = new Book();
		
		Assertions.assertThatThrownBy(() -> this.bookRepository.save(book))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
