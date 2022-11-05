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

import com.biblioteca.entities.RoleModel;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.repository.RoleModelRepository;
import com.biblioteca.requests.UsuarioPostRequestBody;
import com.biblioteca.requests.UsuarioPutRequestBody;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UsuarioCreator;
import com.biblioteca.util.UsuarioPostRequestBodyCreator;
import com.biblioteca.util.UsuarioPutRequestBodyCreator;
import com.biblioteca.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsuarioResourceIT {

	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	@Qualifier(value = "testRestTemplateWithNonRoles")
	private TestRestTemplate testRestTemplateWithNonRoles;
	
	@Autowired
	private RepositorioUsuario repositoryUser;
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	private static RoleModel RoleADMIN = RolesCreator.createAdminRoleModel();
	private static RoleModel RoleUSER = RolesCreator.createUserRoleModel();
	
	private static Usuario ADMIN = UsuarioCreator.createAdminUsuario();
	
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
		@Bean(name = "testRestTemplateWithNonRoles")
		public TestRestTemplate testRestTemplateWithNonRoles(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	@Test
	@DisplayName("findAll Return List Of userDomain Object Inside Page whenSuccessful")
	void findAll_ReturnListOfUserDomainObjectInsidePage_whenSuccessful() {
		roleModelRepository.save(RoleADMIN);
		roleModelRepository.save(RoleUSER);
		
		Usuario usuarioSaved = repositoryUser.save(ADMIN);
		
		PageableResponse<Usuario> usuarioPage = testRestTemplateRoleAdmin.exchange("/usuarios/admin", HttpMethod.GET, null, 
				new ParameterizedTypeReference<PageableResponse<Usuario>>() {
		}).getBody();
		
		Assertions.assertThat(usuarioPage).isNotNull().isNotEmpty();
		Assertions.assertThat(usuarioPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(usuarioPage.toList().get(0)).isEqualTo(usuarioSaved);		
	}
	
	@Test
	@DisplayName("findAll Return List Of Usuario whenSuccessful")
	void findAll_ReturnListUsuario_whenSuccessful() {
		roleModelRepository.save(RoleADMIN);
		roleModelRepository.save(RoleUSER);
		
		Usuario usuarioSaved = repositoryUser.save(ADMIN);
		
		List<Usuario> usuario = testRestTemplateRoleAdmin.exchange("/usuarios/admin/all", HttpMethod.GET, null, 
				 new ParameterizedTypeReference<List<Usuario>>() {
				}).getBody();
		
		Assertions.assertThat(usuario).isNotNull().isNotEmpty();
		Assertions.assertThat(usuario.get(0).getId()).isNotNull();
		Assertions.assertThat(usuario.get(0)).isEqualTo(usuarioSaved);
	}
	
	@Test
	@DisplayName("findByName Return List Of Usuario whenSuccessful")
	void findByName_ReturnListOfUsuario_whenSuccessful() {
		roleModelRepository.save(RoleADMIN);
		roleModelRepository.save(RoleUSER);
		
		Usuario usuarioSaved = repositoryUser.save(ADMIN);
			
		String url = String.format("/usuarios/admin/findname?name=%s",usuarioSaved.getName());
		
		List<Usuario> usuario = testRestTemplateRoleAdmin.exchange(url, HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Usuario>>() {
				}).getBody();		
		
		Assertions.assertThat(usuario).isNotNull().isNotEmpty();
		Assertions.assertThat(usuario.get(0).getId()).isNotNull();
		Assertions.assertThat(usuario.get(0)).isEqualTo(usuarioSaved);
	}
	
	@Test
	@DisplayName("findByName Return Empty List Of Usuario whenSuccessful")
	void findByName_ReturnEmptyListOfUsuario_whenSuccessful() {
		roleModelRepository.save(RoleADMIN);
		roleModelRepository.save(RoleUSER);
		
		repositoryUser.save(ADMIN);
		
		List<Usuario> usuario = testRestTemplateRoleAdmin.exchange("/usuarios/admin/findname?name=test", HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Usuario>>() {
				}).getBody();		
		
		Assertions.assertThat(usuario).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("findById Return Usuario whenSuccessful")
	void findById_ReturnUsuario_whenSuccessful() {
		roleModelRepository.save(RoleADMIN);
		roleModelRepository.save(RoleUSER);
		
		Usuario usuarioSaved = repositoryUser.save(ADMIN);
			
		Usuario usuario = testRestTemplateRoleAdmin.getForObject("/usuarios/admin/{id}", Usuario.class, usuarioSaved.getId());
		
		Assertions.assertThat(usuario).isNotNull();
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario).isEqualTo(usuarioSaved);
	}
	
	@Test
	@DisplayName("GetUser Return UserDomain Authenticated whenSuccessful")
	void GetUser_ReturnUserDomainAuthenticated_whenSuccessful() {
		roleModelRepository.save(RoleADMIN);
		roleModelRepository.save(RoleUSER);
		
		Usuario usuarioSaved = repositoryUser.save(ADMIN);
			
		Usuario usuario = testRestTemplateRoleAdmin.getForObject("/usuarios/users-logged", Usuario.class);
		
		Assertions.assertThat(usuario).isNotNull();
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario).isEqualTo(usuarioSaved);
	}
	
	@Test
	@DisplayName("save save Return UserDomain With RoleAdmin whenSuccessful")
	void save_ReturnUserDomainWithRoleAdmin_whenSuccessful() {
		roleModelRepository.save(RoleADMIN);
		roleModelRepository.save(RoleUSER);
		
		repositoryUser.save(ADMIN);
	
		UsuarioPostRequestBody usuarioPostRequestBody = UsuarioPostRequestBodyCreator.createUserPostRequestBodyCreator();
			
		ResponseEntity<Usuario> entityUsuario = testRestTemplateRoleAdmin.postForEntity("/usuarios/admin/saveAdmin", usuarioPostRequestBody, Usuario.class);
		
		Assertions.assertThat(entityUsuario).isNotNull();
		Assertions.assertThat(entityUsuario.getBody()).isNotNull();
		Assertions.assertThat(entityUsuario.getBody().getId()).isNotNull();
		Assertions.assertThat(entityUsuario.getBody().getName()).isEqualTo(usuarioPostRequestBody.getName());
		Assertions.assertThat(entityUsuario.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("save save Return UserDomain With RoleUser whenSuccessful")
	void save_ReturnUserDomainWithRoleUser_whenSuccessful() {
		roleModelRepository.save(RoleADMIN);
		roleModelRepository.save(RoleUSER);
		
		repositoryUser.save(ADMIN);
		
		UsuarioPostRequestBody usuarioPostRequestBody = UsuarioPostRequestBodyCreator.createUserPostRequestBodyCreator();
			
		ResponseEntity<Usuario> entityUsuario = testRestTemplateRoleAdmin.postForEntity("/usuarios/save", usuarioPostRequestBody, Usuario.class);
		
		Assertions.assertThat(entityUsuario).isNotNull();
		Assertions.assertThat(entityUsuario.getBody()).isNotNull();
		Assertions.assertThat(entityUsuario.getBody().getId()).isNotNull();
		Assertions.assertThat(entityUsuario.getBody().getName()).isEqualTo(usuarioPostRequestBody.getName());
		Assertions.assertThat(entityUsuario.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes Usuario whenSuccessful")
	void delete_RemovesUsuario_whenSuccessful() {
		roleModelRepository.save(RoleADMIN);
		roleModelRepository.save(RoleUSER);
		
		Usuario usuarioSaved =  repositoryUser.save(ADMIN);
			
		ResponseEntity<Void> entityUsuario = testRestTemplateRoleAdmin.exchange("/usuarios/admin/{id}", HttpMethod.DELETE, null, Void.class,usuarioSaved.getId());		
		Assertions.assertThat(entityUsuario).isNotNull();
		Assertions.assertThat(entityUsuario.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace Usuario whenSuccessful")
	void update_ReplaceUsuario_whenSuccessful() {
		roleModelRepository.save(RoleADMIN);
		roleModelRepository.save(RoleUSER);
		
		repositoryUser.save(ADMIN);
	
		UsuarioPutRequestBody usuarioPutRequestBody = UsuarioPutRequestBodyCreator.createUserPutRequestBodyCreator();
			
		ResponseEntity<Void> entityUsuario = testRestTemplateRoleAdmin.exchange("/usuarios", HttpMethod.PUT, 
				new HttpEntity<>(usuarioPutRequestBody), Void.class);
		
		Assertions.assertThat(entityUsuario).isNotNull();
		Assertions.assertThat(entityUsuario.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
}
