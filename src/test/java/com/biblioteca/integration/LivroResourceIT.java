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

import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.requests.LivroPostRequestBody;
import com.biblioteca.util.LivroCreator;
import com.biblioteca.util.LivroPostRequestBodyCreator;
import com.biblioteca.util.UsuarioCreator;
import com.biblioteca.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LivroResourceIT {

	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	private RepositorioLivro repositoryLivro;
	
	@Autowired
	private RepositorioUsuario repositoryUser;
	
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
	@DisplayName("findAll Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		repositoryUser.save(USER);
		
		Livro livroSaved = repositoryLivro.save(LivroCreator.createValidLivro());
		
		PageableResponse<Livro> livroPage = testRestTemplateRoleUser.exchange("/livros", HttpMethod.GET, null, 
				new ParameterizedTypeReference<PageableResponse<Livro>>() {
		}).getBody();
		
		Assertions.assertThat(livroPage).isNotNull().isNotEmpty();
		Assertions.assertThat(livroPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(livroPage.toList().get(0)).isEqualTo(livroSaved);		
	}
	
	@Test
	@DisplayName("findAll Return List Of Livro whenSuccessful")
	void findAll_ReturnListLivro_whenSuccessful() {
		repositoryUser.save(USER);
		
		Livro livroSaved = repositoryLivro.save(LivroCreator.createValidLivro());
		
		List<Livro> livro = testRestTemplateRoleUser.exchange("/livros/all", HttpMethod.GET, null, 
				 new ParameterizedTypeReference<List<Livro>>() {
				}).getBody();
		
		Assertions.assertThat(livro).isNotNull().isNotEmpty();
		Assertions.assertThat(livro.get(0).getId()).isNotNull();
		Assertions.assertThat(livro.get(0)).isEqualTo(livroSaved);
	}
	
	@Test
	@DisplayName("findByName Return List Of Livro whenSuccessful")
	void findByName_ReturnListOfLivro_whenSuccessful() {
		repositoryUser.save(USER);
		
		Livro livroSaved = repositoryLivro.save(LivroCreator.createValidLivro());
			
		String url = String.format("/livros/findbytitulo?titulo=%s",livroSaved.getTitulo());
		
		List<Livro> livro = testRestTemplateRoleUser.exchange(url, HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Livro>>() {
				}).getBody();		
		
		Assertions.assertThat(livro).isNotNull().isNotEmpty();
		Assertions.assertThat(livro.get(0).getId()).isNotNull();
		Assertions.assertThat(livro.get(0)).isEqualTo(livroSaved);
	}
	
	@Test
	@DisplayName("findByName Return Empty List Of Livro whenSuccessful")
	void findByName_ReturnEmptyListOfLivro_whenSuccessful() {
		repositoryUser.save(USER);
		
		List<Livro> livro = testRestTemplateRoleUser.exchange("/livros/findbytitulo?titulo=test", HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Livro>>() {
				}).getBody();		
		
		Assertions.assertThat(livro).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("findById Return Livro whenSuccessful")
	void findById_ReturnLivro_whenSuccessful() {
		repositoryUser.save(USER);
		
		Livro livroSaved = repositoryLivro.save(LivroCreator.createValidLivro());
			
		Livro livro = testRestTemplateRoleUser.getForObject("/livros/{id}", Livro.class, livroSaved.getId());
		
		Assertions.assertThat(livro).isNotNull();
		Assertions.assertThat(livro.getId()).isNotNull();
		Assertions.assertThat(livro).isEqualTo(livroSaved);
	}
	
	@Test
	@DisplayName("save Return Livro whenSuccessful")
	void save_ReturnLivro_whenSuccessful() {
		repositoryUser.save(USER);
	
		
		LivroPostRequestBody livroPostRequestBody = LivroPostRequestBodyCreator.createLivroPostRequestBodyCreator();
			
		ResponseEntity<Livro> entityLivro = testRestTemplateRoleUser.postForEntity("/livros", livroPostRequestBody, Livro.class);
		
		Assertions.assertThat(entityLivro).isNotNull();
		Assertions.assertThat(entityLivro.getBody()).isNotNull();
		Assertions.assertThat(entityLivro.getBody().getId()).isNotNull();
		Assertions.assertThat(entityLivro.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes Livro whenSuccessful")
	void delete_RemovesLivro_whenSuccessful() {
		repositoryUser.save(USER);
		
		Livro livroSaved = repositoryLivro.save(LivroCreator.createValidLivro());
			
		ResponseEntity<Void> entityLivro = testRestTemplateRoleUser.exchange("/livros/{id}", HttpMethod.DELETE, null, Void.class,livroSaved.getId());		
		Assertions.assertThat(entityLivro).isNotNull();
		Assertions.assertThat(entityLivro.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace Livro whenSuccessful")
	void update_ReplaceLivro_whenSuccessful() {
		repositoryUser.save(USER);
		
		Livro livroSaved = repositoryLivro.save(LivroCreator.createValidLivro());
		livroSaved.setTitulo("gabriel dias");
			
		ResponseEntity<Void> entityLivro = testRestTemplateRoleUser.exchange("/livros", HttpMethod.PUT, 
				new HttpEntity<>(livroSaved), Void.class);
		
		Assertions.assertThat(entityLivro).isNotNull();
		Assertions.assertThat(entityLivro.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
}
