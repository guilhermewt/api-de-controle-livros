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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.biblioteca.entities.Loan;
import com.biblioteca.entities.RoleModel;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.repository.LoanRepository;
import com.biblioteca.repository.RoleModelRepository;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.requests.LoanPostRequestBody;
import com.biblioteca.util.BookCreator;
import com.biblioteca.util.DateConvert;
import com.biblioteca.util.LoanCreator;
import com.biblioteca.util.LoanPostRequestBodyCreator;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UserDomainCreator;
import com.biblioteca.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanResourceIT {

	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private UserDomainRepository userDomainRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
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
					.rootUri("http://localhost:" + port)
					.basicAuthentication("guilherme", "biblioteca");
			return new TestRestTemplate(restTemplateBuilder);
		}
		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateRoleUser(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port)
					.basicAuthentication("userBiblioteca", "biblioteca");
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	@Test
	@DisplayName("find all user books by id Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		
		Loan loanSaved = loanRepository.save(LoanCreator.createValidLoan());
		
		PageableResponse<Loan> loanPage = testRestTemplateRoleUser.exchange("/loans", HttpMethod.GET, null, 
				new ParameterizedTypeReference<PageableResponse<Loan>>() {
		}).getBody();
		
		Assertions.assertThat(loanPage).isNotNull().isNotEmpty();
		Assertions.assertThat(loanPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(loanPage.toList().get(0)).isEqualTo(loanSaved);		
	}
	
	@Test
	@DisplayName("find all user books by id Return List Of loan whenSuccessful")
	void findAll_ReturnListloan_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		
		Loan loanSaved = loanRepository.save(LoanCreator.createValidLoan());
		
		List<Loan> loan = testRestTemplateRoleUser.exchange("/loans/all", HttpMethod.GET, null, 
				 new ParameterizedTypeReference<List<Loan>>() {
				}).getBody();
		
		Assertions.assertThat(loan).isNotNull().isNotEmpty();
		Assertions.assertThat(loan.get(0).getId()).isNotNull();
		Assertions.assertThat(loan.get(0)).isEqualTo(loanSaved);
	}
	
	@Test
	@DisplayName("findById Return loan whenSuccessful")
	void findById_Returnloan_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		
		Loan loanSaved = loanRepository.save(LoanCreator.createValidLoan());
			
		Loan loan = testRestTemplateRoleUser.getForObject("/loans/{id}", Loan.class, loanSaved.getId());
		
		Assertions.assertThat(loan).isNotNull();
		Assertions.assertThat(loan.getId()).isNotNull();
		Assertions.assertThat(loan).isEqualTo(loanSaved);
	}
	
	@Test
	@DisplayName("save Return loan whenSuccessful")
	void save_Returnloan_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
	    userDomainRepository.save(USER);
		
		bookRepository.save(BookCreator.createValidBook());
		
		LoanPostRequestBody loanPostRequestBody = LoanPostRequestBodyCreator.createLoanPostRequestBodyCreator();
			
		ResponseEntity<Loan> entityloan = testRestTemplateRoleUser.postForEntity("/loans/{idBook}", loanPostRequestBody, Loan.class,1);
		
		Assertions.assertThat(entityloan).isNotNull();
		Assertions.assertThat(entityloan.getBody()).isNotNull();
		Assertions.assertThat(entityloan.getBody().getId()).isNotNull();
		Assertions.assertThat(entityloan.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes loan whenSuccessful")
	void delete_Removesloan_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		
		Loan loanSaved = loanRepository.save(LoanCreator.createValidLoan());
			
		ResponseEntity<Void> entityloan = testRestTemplateRoleUser.exchange("/loans/{id}", HttpMethod.DELETE, null, Void.class,loanSaved.getId());		
		
		Assertions.assertThat(entityloan).isNotNull();
		Assertions.assertThat(entityloan.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace loan whenSuccessful")
	void update_Replaceloan_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		
		Loan loanSaved = loanRepository.save(LoanCreator.createValidLoan());
		loanSaved.setEndOfLoan(DateConvert.convertData("2023/09/01"));
			
		ResponseEntity<Void> entityloan = testRestTemplateRoleUser.exchange("/loans", HttpMethod.PUT, 
				new HttpEntity<>(loanSaved), Void.class);
		
		Assertions.assertThat(entityloan).isNotNull();
		Assertions.assertThat(entityloan.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
}
