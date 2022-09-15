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

import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioEditora;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.requests.EditoraPostRequestBody;
import com.biblioteca.util.EditoraCreator;
import com.biblioteca.util.EditoraPostRequestBodyCreator;
import com.biblioteca.util.UsuarioCreator;
import com.biblioteca.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EditoraResourceIT {

	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	private RepositorioEditora repositoryEditora;
	
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
		
		Editora editoraSaved = repositoryEditora.save(EditoraCreator.createEditoraToBeSaved());
		
		PageableResponse<Editora> editoraPage = testRestTemplateRoleUser.exchange("/editoras", HttpMethod.GET, null, 
				new ParameterizedTypeReference<PageableResponse<Editora>>() {
		}).getBody();
		
		Assertions.assertThat(editoraPage).isNotNull().isNotEmpty();
		Assertions.assertThat(editoraPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(editoraPage.toList().get(0)).isEqualTo(editoraSaved);		
	}
	
	@Test
	@DisplayName("findAll Return List Of Editora whenSuccessful")
	void findAll_ReturnListEditora_whenSuccessful() {
		repositoryUser.save(USER);
		
		Editora editoraSaved = repositoryEditora.save(EditoraCreator.createEditoraToBeSaved());
		
		List<Editora> editora = testRestTemplateRoleUser.exchange("/editoras/all", HttpMethod.GET, null, 
				 new ParameterizedTypeReference<List<Editora>>() {
				}).getBody();
		
		Assertions.assertThat(editora).isNotNull().isNotEmpty();
		Assertions.assertThat(editora.get(0).getId()).isNotNull();
		Assertions.assertThat(editora.get(0)).isEqualTo(editoraSaved);
	}
	
	@Test
	@DisplayName("findByName Return List Of Editora whenSuccessful")
	void findByName_ReturnListOfEditora_whenSuccessful() {
		repositoryUser.save(USER);
		
		Editora editoraSaved = repositoryEditora.save(EditoraCreator.createEditoraToBeSaved());
			
		String url = String.format("/editoras/findByName?name=%s",editoraSaved.getNome());
		
		List<Editora> editora = testRestTemplateRoleUser.exchange(url, HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Editora>>() {
				}).getBody();		
		
		Assertions.assertThat(editora).isNotNull().isNotEmpty();
		Assertions.assertThat(editora.get(0).getId()).isNotNull();
		Assertions.assertThat(editora.get(0)).isEqualTo(editoraSaved);
	}
	
	@Test
	@DisplayName("findByName Return Empty List Of Editora whenSuccessful")
	void findByName_ReturnEmptyListOfEditora_whenSuccessful() {
		repositoryUser.save(USER);
		
		List<Editora> editora = testRestTemplateRoleUser.exchange("/editoras/findByName?name=test", HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Editora>>() {
				}).getBody();		
		
		Assertions.assertThat(editora).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("findById Return Editora whenSuccessful")
	void findById_ReturnEditora_whenSuccessful() {
		repositoryUser.save(USER);
		
		Editora editoraSaved = repositoryEditora.save(EditoraCreator.createEditoraToBeSaved());
			
		Editora editora = testRestTemplateRoleUser.getForObject("/editoras/{id}", Editora.class, editoraSaved.getId());
		
		Assertions.assertThat(editora).isNotNull();
		Assertions.assertThat(editora.getId()).isNotNull();
		Assertions.assertThat(editora).isEqualTo(editoraSaved);
	}
	
	@Test
	@DisplayName("save Return Editora whenSuccessful")
	void save_ReturnEditora_whenSuccessful() {
		repositoryUser.save(USER);
		
		EditoraPostRequestBody editoraPostRequestBody = EditoraPostRequestBodyCreator.createEditoraPostRequestBodyCreator();
			
		ResponseEntity<Editora> entityEditora = testRestTemplateRoleUser.postForEntity("/editoras", editoraPostRequestBody, Editora.class);
		
		Assertions.assertThat(entityEditora).isNotNull();
		Assertions.assertThat(entityEditora.getBody()).isNotNull();
		Assertions.assertThat(entityEditora.getBody().getId()).isNotNull();
		Assertions.assertThat(entityEditora.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes Editora whenSuccessful")
	void delete_RemovesEditora_whenSuccessful() {
		repositoryUser.save(USER);
		
		Editora editoraSaved = repositoryEditora.save(EditoraCreator.createEditoraToBeSaved());
			
		ResponseEntity<Void> entityEditora = testRestTemplateRoleUser.exchange("/editoras/{id}", HttpMethod.DELETE, null, Void.class,editoraSaved.getId());		
		Assertions.assertThat(entityEditora).isNotNull();
		Assertions.assertThat(entityEditora.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace Editora whenSuccessful")
	void update_ReplaceEditora_whenSuccessful() {
		repositoryUser.save(USER);
		
		Editora editoraSaved = repositoryEditora.save(EditoraCreator.createEditoraToBeSaved());
		editoraSaved.setNome("gabriel dias");
			
		ResponseEntity<Void> entityEditora = testRestTemplateRoleUser.exchange("/editoras", HttpMethod.PUT, 
				new HttpEntity<>(editoraSaved), Void.class);
		
		Assertions.assertThat(entityEditora).isNotNull();
		Assertions.assertThat(entityEditora.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
}
