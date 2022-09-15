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

import com.biblioteca.entities.Autor;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioAutor;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.requests.AutorPostRequestBody;
import com.biblioteca.requests.AutorPutRequestBody;
import com.biblioteca.util.AutorCreator;
import com.biblioteca.util.AutorPostRequestBodyCreator;
import com.biblioteca.util.AutorPutRequestBodyCreator;
import com.biblioteca.util.UsuarioCreator;
import com.biblioteca.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AutorResourceIT {

	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	private RepositorioAutor repositoryAutor;
	
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
		
		Autor autorSaved = repositoryAutor.save(AutorCreator.createAutorToBeSaved());
		
		PageableResponse<Autor> autorPage = testRestTemplateRoleUser.exchange("/autores", HttpMethod.GET, null, 
				new ParameterizedTypeReference<PageableResponse<Autor>>() {
		}).getBody();
		
		Assertions.assertThat(autorPage).isNotNull().isNotEmpty();
		Assertions.assertThat(autorPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(autorPage.toList().get(0)).isEqualTo(autorSaved);		
	}
	
	@Test
	@DisplayName("findAll Return List Of Autor whenSuccessful")
	void findAll_ReturnListAutor_whenSuccessful() {
		repositoryUser.save(USER);
		
		Autor autorSaved = repositoryAutor.save(AutorCreator.createAutorToBeSaved());
		
		List<Autor> autor = testRestTemplateRoleUser.exchange("/autores/all", HttpMethod.GET, null, 
				 new ParameterizedTypeReference<List<Autor>>() {
				}).getBody();
		
		Assertions.assertThat(autor).isNotNull().isNotEmpty();
		Assertions.assertThat(autor.get(0).getId()).isNotNull();
		Assertions.assertThat(autor.get(0)).isEqualTo(autorSaved);
	}
	
	@Test
	@DisplayName("findByName Return List Of Autor whenSuccessful")
	void findByName_ReturnListOfAutor_whenSuccessful() {
		repositoryUser.save(USER);
		
		Autor autorSaved = repositoryAutor.save(AutorCreator.createAutorToBeSaved());
			
		String url = String.format("/autores/find?name=%s",autorSaved.getNome());
		
		List<Autor> autor = testRestTemplateRoleUser.exchange(url, HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Autor>>() {
				}).getBody();		
		
		Assertions.assertThat(autor).isNotNull().isNotEmpty();
		Assertions.assertThat(autor.get(0).getId()).isNotNull();
		Assertions.assertThat(autor.get(0)).isEqualTo(autorSaved);
	}
	
	@Test
	@DisplayName("findByName Return Empty List Of Autor whenSuccessful")
	void findByName_ReturnEmptyListOfAutor_whenSuccessful() {
		repositoryUser.save(USER);
		
		List<Autor> autor = testRestTemplateRoleUser.exchange("/autores/find?name=test", HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Autor>>() {
				}).getBody();		
		
		Assertions.assertThat(autor).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("findById Return Autor whenSuccessful")
	void findById_ReturnAutor_whenSuccessful() {
		repositoryUser.save(USER);
		
		Autor autorSaved = repositoryAutor.save(AutorCreator.createAutorToBeSaved());
			
		Autor autor = testRestTemplateRoleUser.getForObject("/autores/{id}", Autor.class, autorSaved.getId());
		
		Assertions.assertThat(autor).isNotNull();
		Assertions.assertThat(autor.getId()).isNotNull();
		Assertions.assertThat(autor).isEqualTo(autorSaved);
	}
	
	@Test
	@DisplayName("save Return Autor whenSuccessful")
	void save_ReturnAutor_whenSuccessful() {
		repositoryUser.save(USER);
		
		AutorPostRequestBody autorPostRequestBody = AutorPostRequestBodyCreator.createAutorPostRequestBodyCreator();
			
		ResponseEntity<Autor> entityAutor = testRestTemplateRoleUser.postForEntity("/autores", autorPostRequestBody, Autor.class);
		
		Assertions.assertThat(entityAutor).isNotNull();
		Assertions.assertThat(entityAutor.getBody()).isNotNull();
		Assertions.assertThat(entityAutor.getBody().getId()).isNotNull();
		Assertions.assertThat(entityAutor.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes Autor whenSuccessful")
	void delete_RemovesAutor_whenSuccessful() {
		repositoryUser.save(USER);
		
		Autor autorSaved = repositoryAutor.save(AutorCreator.createAutorToBeSaved());
			
		ResponseEntity<Void> entityAutor = testRestTemplateRoleUser.exchange("/autores/{id}", HttpMethod.DELETE, null, Void.class,autorSaved.getId());		
		Assertions.assertThat(entityAutor).isNotNull();
		Assertions.assertThat(entityAutor.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace Autor whenSuccessful")
	void update_ReplaceAutor_whenSuccessful() {
		repositoryUser.save(USER);
		
		Autor autorSaved = repositoryAutor.save(AutorCreator.createAutorToBeSaved());
		autorSaved.setNome("gabriel dias");
			
		ResponseEntity<Void> entityAutor = testRestTemplateRoleUser.exchange("/autores", HttpMethod.PUT, 
				new HttpEntity<>(autorSaved), Void.class);
		
		Assertions.assertThat(entityAutor).isNotNull();
		Assertions.assertThat(entityAutor.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
}
