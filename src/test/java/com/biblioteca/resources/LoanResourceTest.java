package com.biblioteca.resources;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.biblioteca.entities.Loan;
import com.biblioteca.requests.LoanPostRequestBody;
import com.biblioteca.services.LoanService;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.LoanCreator;
import com.biblioteca.util.LoanPostRequestBodyCreator;
import com.biblioteca.util.LoanPutRequestBodyCreator;
import com.biblioteca.util.UserDomainCreator;

@ExtendWith(SpringExtension.class)
public class LoanResourceTest {
	
	@InjectMocks
	private LoanResources loanResource;
	
	@Mock
	private LoanService loanServiceMock;
	
	@Mock
	private GetUserDetails userAuthenticated;
	
	@BeforeEach
	void setUp() {
		PageImpl<Loan> loanPage = new PageImpl<>(List.of(LoanCreator.createValidLoan()));
		BDDMockito.when(loanServiceMock.findAll(ArgumentMatchers.any())).thenReturn(loanPage);	
		
		BDDMockito.when(loanServiceMock.findAllNonPageable())
		.thenReturn(List.of(LoanCreator.createValidLoan()));
		
		BDDMockito.when(loanServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
		.thenReturn(LoanCreator.createValidLoan());
		
		BDDMockito.when(loanServiceMock.save(ArgumentMatchers.any(LoanPostRequestBody.class), ArgumentMatchers.anyLong()))
		.thenReturn(LoanCreator.createValidLoan());
		
		BDDMockito.doNothing().when(loanServiceMock).delete(ArgumentMatchers.anyLong());
		
		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());
		
	}
	
	@Test
	@DisplayName("find all user books by id Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Loan loanSaved = LoanCreator.createValidLoan();
		
		Page<Loan> loan = this.loanResource.findAll(null).getBody();
		
		Assertions.assertThat(loan).isNotNull().isNotEmpty();
		Assertions.assertThat(loan.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(loan.toList().get(0)).isEqualTo(loanSaved);		
	}
	
	@Test
	@DisplayName("find all user books by id Return List of loan whenSuccessful")
	void findAll_ReturnListOfloan_whenSuccessful() {
		Loan loanSaved = LoanCreator.createValidLoan();
		
		List<Loan> loan = this.loanResource.findAllNonPageable().getBody();
		
		Assertions.assertThat(loan).isNotNull().isNotEmpty();
		Assertions.assertThat(loan.get(0).getId()).isNotNull();
		Assertions.assertThat(loan.get(0)).isEqualTo(loanSaved);		
	}
	
	@Test
	@DisplayName("findById Return loan whenSuccessful")
	void findById_Returnloan_whenSuccessful() {
		Loan loanSaved = LoanCreator.createValidLoan();
		
		Loan loan = this.loanResource.findById(1).getBody();
		
		Assertions.assertThat(loan).isNotNull();
		Assertions.assertThat(loan.getId()).isNotNull();
		Assertions.assertThat(loan).isEqualTo(loanSaved);		
	}
	
	@Test
	@DisplayName("save Return loan whenSuccessful")
	void save_Returnloan_whenSuccessful() {
		Loan loanSaved = LoanCreator.createValidLoan();
		
		Loan loan = this.loanResource.save(LoanPostRequestBodyCreator.createLoanPostRequestBodyCreator(),1l)
				.getBody();
		
		Assertions.assertThat(loan).isNotNull();
		Assertions.assertThat(loan.getId()).isNotNull();
		Assertions.assertThat(loan).isEqualTo(loanSaved);		
	}
	
	@Test
	@DisplayName("delete removes loan whenSuccessful")
	void delete_removesloan_whenSuccessful() {		
		ResponseEntity<Void> loan = this.loanResource.delete(1);
		
		Assertions.assertThat(loan.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace loan whenSuccessful")
	void update_replaceloan_whenSuccessful() {		
	    this.loanResource.save(LoanPostRequestBodyCreator.createLoanPostRequestBodyCreator(),1l).getBody();
		
		ResponseEntity<Void> loan = this.loanResource.update(LoanPutRequestBodyCreator.createLoanPutRequestBodyCreator());
		
		Assertions.assertThat(loan.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);					
	}
	
}
