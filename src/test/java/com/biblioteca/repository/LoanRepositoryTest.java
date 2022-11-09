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
import com.biblioteca.entities.Loan;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.util.BookCreator;
import com.biblioteca.util.LoanCreator;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UserDomainCreator;

@DataJpaTest
@DisplayName("test for loan repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanRepositoryTest {
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private UserDomainRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	
	@Test
	@DisplayName("find all user books by id return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomain = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		Loan loanToBeSaved = this.loanRepository.save(LoanCreator.createValidLoan());
		Page<Loan> loanSaved = this.loanRepository.findByUserDomainId(userDomain.getId(), PageRequest.of(0, 5));
		
		Assertions.assertThat(loanSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(loanSaved.toList().get(0)).isEqualTo(loanToBeSaved);
	}
	
	@Test
	@DisplayName("find all user books by id return list of loan whensuccessful")
	void findAll_returnListOfloan_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		Loan loanToBeSaved = this.loanRepository.save(LoanCreator.createValidLoan());
		List<Loan> loanSaved = this.loanRepository.findAll();
		
		Assertions.assertThat(loanSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(loanSaved.get(0)).isEqualTo(loanToBeSaved);
	}
	
	@Test
	@DisplayName("findById return loan whenSuccessful")
	void findByid_returnloan_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomain = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		Loan loanToBeSaved = this.loanRepository.save(LoanCreator.createValidLoan());
		Loan loanSaved = this.loanRepository.findAuthenticatedUserById(loanToBeSaved.getId(),userDomain.getId()).get();
		
		Assertions.assertThat(loanSaved).isNotNull();
		Assertions.assertThat(loanSaved.getId()).isNotNull();
		Assertions.assertThat(loanSaved).isEqualTo(loanToBeSaved);
	}
	
	
	
	@Test
	@DisplayName("save return loan whenSuccessful")
	void save_returnloan_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		Book livro = this.bookRepository.save(BookCreator.createValidBook());
		
		Loan loanToBeSaved = LoanCreator.createValidLoan();
		loanToBeSaved.getBooks().add(livro);
		
		Loan loanSaved = this.loanRepository.save(loanToBeSaved);
		
		Assertions.assertThat(loanSaved).isNotNull();
		Assertions.assertThat(loanSaved.getId()).isNotNull();
		Assertions.assertThat(loanSaved).isEqualTo(loanToBeSaved);
	}
	
	@Test
	@DisplayName("delete removes loan whenSuccessful")
	void delete_removesloan_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomain = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		Book livro = this.bookRepository.save(BookCreator.createValidBook());
		
		Loan loanToBeSaved = LoanCreator.createValidLoan();
		loanToBeSaved.getBooks().add(livro);
		
		Loan loanSaved = this.loanRepository.save(loanToBeSaved);
		
	    this.loanRepository.deleteAuthenticatedUserLoanById(livro.getId(), userDomain.getId());;
	    
	    Optional<Loan> loanDeleted = this.loanRepository.findAuthenticatedUserById(loanSaved.getId(),userDomain.getId());
	    
	    Assertions.assertThat(loanDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace loan whenSuccessful")
	void update_replaceloan_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		this.loanRepository.save(LoanCreator.createValidLoan());
		
		Loan loanToBeUpdate = LoanCreator.createValidLoan();
		
	    Loan loanUpdate = this.loanRepository.save(loanToBeUpdate);
	    
	    Assertions.assertThat(loanUpdate).isNotNull();
	    Assertions.assertThat(loanUpdate.getId()).isNotNull();
	    Assertions.assertThat(loanUpdate).isEqualTo(loanToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when loan name is empty")
	void save_throwConstrationViolationException_whenloanNameIsEmpty() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		Loan loan = new Loan();
		
		Assertions.assertThatThrownBy(() -> this.loanRepository.save(loan))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
