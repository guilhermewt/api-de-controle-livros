package com.biblioteca.service;

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

import com.biblioteca.entities.Loan;
import com.biblioteca.repository.LoanRepository;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.repository.RoleModelRepository;
import com.biblioteca.services.LoanService;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.LoanCreator;
import com.biblioteca.util.LoanPostRequestBodyCreator;
import com.biblioteca.util.LoanPutRequestBodyCreator;
import com.biblioteca.util.BookCreator;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UserDomainCreator;

@ExtendWith(SpringExtension.class)
public class LoanServiceTest {
	
	@InjectMocks
	private LoanService loanService;
	
	@Mock
	private LoanRepository loanRepositoryMock;
	
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
		PageImpl<Loan> loanPage = new PageImpl<>(List.of(LoanCreator.createValidLoan()));
		BDDMockito.when(loanRepositoryMock.findByUserDomainId(ArgumentMatchers.anyLong(),ArgumentMatchers.any(PageRequest.class)))
		.thenReturn(loanPage);
		
		BDDMockito.when(loanRepositoryMock.findByUserDomainId(ArgumentMatchers.anyLong())).thenReturn(List.of(LoanCreator.createValidLoan()));
		
		BDDMockito.when(loanRepositoryMock.findAuthenticatedUserById(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(LoanCreator.createValidLoan()));
		
		BDDMockito.when(loanRepositoryMock.save(ArgumentMatchers.any(Loan.class))).thenReturn(LoanCreator.createValidLoan());
		
		BDDMockito.doNothing().when(loanRepositoryMock).deleteAuthenticatedUserLoanById(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
		
		BDDMockito.when(bookRepositoryMock.findAuthenticatedUserBooksById(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(BookCreator.createValidBook()));
		
		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		BDDMockito.when(userDomainRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(UserDomainCreator.createUserDomainWithRoleADMIN()));
		
		BDDMockito.when(roleModelRepository.findById(ArgumentMatchers.eq(1l))).thenReturn(Optional.of(RolesCreator.createAdminRoleModel()));
		BDDMockito.when(roleModelRepository.findById(ArgumentMatchers.eq(2l))).thenReturn(Optional.of(RolesCreator.createUserRoleModel()));
	}
	
	@Test
	@DisplayName("find all user books by id Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Loan loan = LoanCreator.createValidLoan();
		
		Page<Loan> loanPage = this.loanService.findAll(PageRequest.of(0, 1));
		
		Assertions.assertThat(loanPage).isNotNull().isNotEmpty();
		Assertions.assertThat(loanPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(loanPage.toList().get(0)).isEqualTo(loan);
	}
	
	@Test
	@DisplayName("find all user books by id Return List Of loan whenSuccessful")
	void findAll_ReturnListOfloan_whenSuccessful() {
		Loan loanSaved = LoanCreator.createValidLoan();
		
		List<Loan> loan = this.loanService.findAllNonPageable();
		
		Assertions.assertThat(loan).isNotNull().isNotEmpty();
		Assertions.assertThat(loan.get(0).getId()).isNotNull();
		Assertions.assertThat(loan.get(0)).isEqualTo(loanSaved);
	}
	
	@Test
	@DisplayName("findById return loan whenSuccessful")
	void findById_Returnloan_whenSuccessful() {
		Loan loanSaved = LoanCreator.createValidLoan();
		
		Loan loan = this.loanService.findByIdOrElseThrowResourceNotFoundException(1);
		
		Assertions.assertThat(loan).isNotNull();
		Assertions.assertThat(loan.getId()).isNotNull();
		Assertions.assertThat(loan).isEqualTo(loanSaved);
	}
	
	@Test
	@DisplayName("findById Return Bad Request Exception When loan Is Not Found")
	void findById_ReturnBadRequestExceptionWhenloanIsNotFound() {
		BDDMockito.when(loanRepositoryMock.findAuthenticatedUserById(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatCode(() -> this.loanService.findByIdOrElseThrowResourceNotFoundException(1))
		.isInstanceOf(BadRequestException.class);
	}
	
	
	
	@Test
	@DisplayName("save Return loan whenSuccessful")
	void save_Returnloan_whenSuccessful() {
		Loan loanSaved = LoanCreator.createValidLoan();
		
		Loan loan = this.loanService.save(LoanPostRequestBodyCreator.createLoanPostRequestBodyCreator(),1l);
		
		Assertions.assertThat(loan).isNotNull();
		Assertions.assertThat(loan.getId()).isNotNull();
		Assertions.assertThat(loan).isEqualTo(loanSaved);
	}
	
	@Test
	@DisplayName("delete Removes loan whenSuccessful")
	void delete_Removesloan_whenSuccessful() {	
		Assertions.assertThatCode(() -> this.loanService.delete(1l)).doesNotThrowAnyException();
	}
	
	@Test
	@DisplayName("update Replace loan whenSuccessful")
	void update_Replaveloan_whenSuccessful() {			
		Assertions.assertThatCode(() -> this.loanService.update(LoanPutRequestBodyCreator.createLoanPutRequestBodyCreator())).doesNotThrowAnyException();
	}
	
}
