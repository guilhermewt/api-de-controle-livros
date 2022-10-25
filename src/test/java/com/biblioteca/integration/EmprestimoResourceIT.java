package com.biblioteca.integration;

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

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioEmprestimo;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.requests.EmprestimosPostRequestBody;
import com.biblioteca.util.DateConvert;
import com.biblioteca.util.EmprestimoCreator;
import com.biblioteca.util.EmprestimoPostRequestBodyCreator;
import com.biblioteca.util.LivroCreator;
import com.biblioteca.util.UsuarioCreator;
import com.biblioteca.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmprestimoResourceIT {

	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	private RepositorioEmprestimo repositoryEmprestimo;
	
	@Autowired
	private RepositorioUsuario repositoryUser;
	
	@Autowired
	private RepositorioLivro repositoryLivro;
	
	
	private static Usuario USER = UsuarioCreator.createUserUsuario();
	
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
		repositoryUser.save(USER);
		
		Emprestimo emprestimoSaved = repositoryEmprestimo.save(EmprestimoCreator.createValidEmprestimo());
		
		PageableResponse<Emprestimo> emprestimoPage = testRestTemplateRoleUser.exchange("/emprestimos", HttpMethod.GET, null, 
				new ParameterizedTypeReference<PageableResponse<Emprestimo>>() {
		}).getBody();
		
		Assertions.assertThat(emprestimoPage).isNotNull().isNotEmpty();
		Assertions.assertThat(emprestimoPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(emprestimoPage.toList().get(0)).isEqualTo(emprestimoSaved);		
	}
	
	@Test
	@DisplayName("find all user books by id Return List Of Emprestimo whenSuccessful")
	void findAll_ReturnListEmprestimo_whenSuccessful() {
		repositoryUser.save(USER);
		
		Emprestimo emprestimoSaved = repositoryEmprestimo.save(EmprestimoCreator.createValidEmprestimo());
		
		List<Emprestimo> emprestimo = testRestTemplateRoleUser.exchange("/emprestimos/all", HttpMethod.GET, null, 
				 new ParameterizedTypeReference<List<Emprestimo>>() {
				}).getBody();
		
		Assertions.assertThat(emprestimo).isNotNull().isNotEmpty();
		Assertions.assertThat(emprestimo.get(0).getId()).isNotNull();
		Assertions.assertThat(emprestimo.get(0)).isEqualTo(emprestimoSaved);
	}
	
	@Test
	@DisplayName("findById Return Emprestimo whenSuccessful")
	void findById_ReturnEmprestimo_whenSuccessful() {
		repositoryUser.save(USER);
		
		Emprestimo emprestimoSaved = repositoryEmprestimo.save(EmprestimoCreator.createValidEmprestimo());
			
		Emprestimo emprestimo = testRestTemplateRoleUser.getForObject("/emprestimos/{id}", Emprestimo.class, emprestimoSaved.getId());
		
		Assertions.assertThat(emprestimo).isNotNull();
		Assertions.assertThat(emprestimo.getId()).isNotNull();
		Assertions.assertThat(emprestimo).isEqualTo(emprestimoSaved);
	}
	
	@Test
	@DisplayName("save Return Emprestimo whenSuccessful")
	void save_ReturnEmprestimo_whenSuccessful() {
	    repositoryUser.save(USER);
		
		repositoryLivro.save(LivroCreator.createValidLivro());
		
		EmprestimosPostRequestBody emprestimoPostRequestBody = EmprestimoPostRequestBodyCreator.createEmprestimoPostRequestBodyCreator();
			
		ResponseEntity<Emprestimo> entityEmprestimo = testRestTemplateRoleUser.postForEntity("/emprestimos/{id}", emprestimoPostRequestBody, Emprestimo.class,1);
		
		Assertions.assertThat(entityEmprestimo).isNotNull();
		Assertions.assertThat(entityEmprestimo.getBody()).isNotNull();
		Assertions.assertThat(entityEmprestimo.getBody().getId()).isNotNull();
		Assertions.assertThat(entityEmprestimo.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes Emprestimo whenSuccessful")
	void delete_RemovesEmprestimo_whenSuccessful() {
		repositoryUser.save(USER);
		
		Emprestimo emprestimoSaved = repositoryEmprestimo.save(EmprestimoCreator.createValidEmprestimo());
			
		ResponseEntity<Void> entityEmprestimo = testRestTemplateRoleUser.exchange("/emprestimos/{id}", HttpMethod.DELETE, null, Void.class,emprestimoSaved.getId());		
		Assertions.assertThat(entityEmprestimo).isNotNull();
		Assertions.assertThat(entityEmprestimo.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace Emprestimo whenSuccessful")
	void update_ReplaceEmprestimo_whenSuccessful() {
		repositoryUser.save(USER);
		
		Emprestimo emprestimoSaved = repositoryEmprestimo.save(EmprestimoCreator.createValidEmprestimo());
		emprestimoSaved.setDataDevolucao(DateConvert.convertData("2023/09/01"));
			
		ResponseEntity<Void> entityEmprestimo = testRestTemplateRoleUser.exchange("/emprestimos", HttpMethod.PUT, 
				new HttpEntity<>(emprestimoSaved), Void.class);
		
		Assertions.assertThat(entityEmprestimo).isNotNull();
		Assertions.assertThat(entityEmprestimo.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
}
