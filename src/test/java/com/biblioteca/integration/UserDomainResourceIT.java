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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.biblioteca.data.JwtObject;
import com.biblioteca.entities.RoleModel;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.repository.RoleModelRepository;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.requests.LoginGetRequestBody;
import com.biblioteca.requests.UserDomainPostRequestBody;
import com.biblioteca.requests.UserDomainPutRequestBody;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UserDomainCreator;
import com.biblioteca.util.UserDomainPostRequestBodyCreator;
import com.biblioteca.util.UserDomainPutRequestBodyCreator;
import com.biblioteca.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles(profiles = "test")
public class UserDomainResourceIT {

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
	private UserDomainRepository repositoryUser;
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	private static RoleModel RoleADMIN = RolesCreator.createAdminRoleModel();
	private static RoleModel RoleUSER = RolesCreator.createUserRoleModel();
	
	private static UserDomain ADMIN = UserDomainCreator.createUserDomainWithRoleADMIN();
	
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
	
	//tentar colocar user/admin e fazer uma condicional e escolher o jwtobject
	
	public  HttpHeaders httpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtObject().getToken());
        return httpHeaders;
    }
	

	public JwtObject jwtObject() {		
		LoginGetRequestBody login = new LoginGetRequestBody("guilherme","biblioteca");		
		ResponseEntity<JwtObject> jwt = testRestTemplateRoleAdmin.postForEntity("/login", login, JwtObject.class);		
        return jwt.getBody();
	}
	
	@Test
	@DisplayName("findAll Return List Of userDomain Object Inside Page whenSuccessful")
	void findAll_ReturnListOfUserDomainObjectInsidePage_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		UserDomain userDomainSaved = repositoryUser.save(ADMIN);
		
		PageableResponse<UserDomain> userDomainPage = testRestTemplateRoleAdmin.exchange("/userDomains/admin", HttpMethod.GET, new HttpEntity<>(httpHeaders()), 
				new ParameterizedTypeReference<PageableResponse<UserDomain>>() {
		}).getBody();
		
		Assertions.assertThat(userDomainPage).isNotNull().isNotEmpty();
		Assertions.assertThat(userDomainPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userDomainPage.toList().get(0)).isEqualTo(userDomainSaved);		
	}
	
	@Test
	@DisplayName("findAll Return List Of userDomain whenSuccessful")
	void findAll_ReturnListuserDomain_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		UserDomain userDomainSaved = repositoryUser.save(ADMIN);
		
		List<UserDomain> userDomain = testRestTemplateRoleAdmin.exchange("/userDomains/admin/all", HttpMethod.GET, new HttpEntity<>(httpHeaders()), 
				 new ParameterizedTypeReference<List<UserDomain>>() {
				}).getBody();
		
		Assertions.assertThat(userDomain).isNotNull().isNotEmpty();
		Assertions.assertThat(userDomain.get(0).getId()).isNotNull();
		Assertions.assertThat(userDomain.get(0)).isEqualTo(userDomainSaved);
	}
	
	@Test
	@DisplayName("findByName Return List Of userDomain whenSuccessful")
	void findByName_ReturnListOfuserDomain_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		UserDomain userDomainSaved = repositoryUser.save(ADMIN);
			
		String url = String.format("/userDomains/admin/findname?name=%s",userDomainSaved.getName());
		
		List<UserDomain> userDomain = testRestTemplateRoleAdmin.exchange(url, HttpMethod.GET,new HttpEntity<>(httpHeaders()),
				new ParameterizedTypeReference<List<UserDomain>>() {
				}).getBody();		
		
		Assertions.assertThat(userDomain).isNotNull().isNotEmpty();
		Assertions.assertThat(userDomain.get(0).getId()).isNotNull();
		Assertions.assertThat(userDomain.get(0)).isEqualTo(userDomainSaved);
	}
	
	@Test
	@DisplayName("findByName Return Empty List Of userDomain whenSuccessful")
	void findByName_ReturnEmptyListOfuserDomain_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		repositoryUser.save(ADMIN);
		
		List<UserDomain> userDomain = testRestTemplateRoleAdmin.exchange("/userDomains/admin/findname?name=test", HttpMethod.GET,new HttpEntity<>(httpHeaders()),
				new ParameterizedTypeReference<List<UserDomain>>() {
				}).getBody();		
		
		Assertions.assertThat(userDomain).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("findById Return userDomain whenSuccessful")
	void findById_ReturnuserDomain_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		UserDomain userDomainSaved = repositoryUser.save(ADMIN);
			
		UserDomain userDomain = testRestTemplateRoleAdmin.exchange("/userDomains/admin/{id}", HttpMethod.GET, new HttpEntity<>(httpHeaders()),UserDomain.class,userDomainSaved.getId()).getBody();
		
		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}
	
	@Test
	@DisplayName("GetUser Return UserDomain Authenticated whenSuccessful")
	void GetUser_ReturnUserDomainAuthenticated_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		UserDomain userDomainSaved = repositoryUser.save(ADMIN);
			
		UserDomain userDomain = testRestTemplateRoleAdmin.exchange("/userDomains/users-logged",HttpMethod.GET,new HttpEntity<>(httpHeaders()),UserDomain.class).getBody();	
		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}
	
	@Test
	@DisplayName("save save Return UserDomain With RoleAdmin whenSuccessful")
	void save_ReturnUserDomainWithRoleAdmin_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		repositoryUser.save(ADMIN);
	
		UserDomainPostRequestBody userDomainPostRequestBody = UserDomainPostRequestBodyCreator.createUserPostRequestBodyCreator();
			
		ResponseEntity<UserDomain> entityuserDomain = testRestTemplateRoleAdmin.exchange("/userDomains/admin/saveAdmin", HttpMethod.POST , new HttpEntity<>(userDomainPostRequestBody,httpHeaders()),UserDomain.class);
		
		Assertions.assertThat(entityuserDomain).isNotNull();
		Assertions.assertThat(entityuserDomain.getBody()).isNotNull();
		Assertions.assertThat(entityuserDomain.getBody().getId()).isNotNull();
		Assertions.assertThat(entityuserDomain.getBody().getName()).isEqualTo(userDomainPostRequestBody.getName());
		Assertions.assertThat(entityuserDomain.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("save save Return UserDomain With RoleUser whenSuccessful")
	void save_ReturnUserDomainWithRoleUser_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		repositoryUser.save(ADMIN);
		
		UserDomainPostRequestBody userDomainPostRequestBody = UserDomainPostRequestBodyCreator.createUserPostRequestBodyCreator();
			
		ResponseEntity<UserDomain> entityuserDomain = testRestTemplateRoleAdmin.exchange("/userDomains/save", HttpMethod.POST, new HttpEntity<>(userDomainPostRequestBody,httpHeaders()),UserDomain.class);
		
		Assertions.assertThat(entityuserDomain).isNotNull();
		Assertions.assertThat(entityuserDomain.getBody()).isNotNull();
		Assertions.assertThat(entityuserDomain.getBody().getId()).isNotNull();
		Assertions.assertThat(entityuserDomain.getBody().getName()).isEqualTo(userDomainPostRequestBody.getName());
		Assertions.assertThat(entityuserDomain.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes userDomain whenSuccessful")
	void delete_RemovesuserDomain_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		UserDomain userDomainSaved =  repositoryUser.save(ADMIN);
			
		ResponseEntity<Void> entityuserDomain = testRestTemplateRoleAdmin.exchange("/userDomains/admin/{id}", HttpMethod.DELETE, null, Void.class,userDomainSaved.getId());		
		Assertions.assertThat(entityuserDomain).isNotNull();
		Assertions.assertThat(entityuserDomain.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace userDomain whenSuccessful")
	void update_ReplaceuserDomain_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		repositoryUser.save(ADMIN);
	
		UserDomainPutRequestBody userDomainPutRequestBody = UserDomainPutRequestBodyCreator.createUserDomainPutRequestBodyCreator();
			
		ResponseEntity<Void> entityuserDomain = testRestTemplateRoleAdmin.exchange("/userDomains", HttpMethod.PUT, 
				new HttpEntity<>(userDomainPutRequestBody,httpHeaders()), Void.class);
		
		Assertions.assertThat(entityuserDomain).isNotNull();
		Assertions.assertThat(entityuserDomain.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update userdomain password whenSuccessful")
	void update_userDomainPassword_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		
		repositoryUser.save(ADMIN);
	
		UserDomainPutRequestBody userDomainPutRequestBody = UserDomainPutRequestBodyCreator.createUserDomainPutRequestBodyCreator();
			
		ResponseEntity<Void> entityuserDomain = testRestTemplateRoleAdmin.exchange("/userDomains/update-password?oldPassword=biblioteca&newPassword=123", HttpMethod.PUT, 
				new HttpEntity<>(userDomainPutRequestBody,httpHeaders()), Void.class);
		
		Assertions.assertThat(entityuserDomain).isNotNull();
		Assertions.assertThat(entityuserDomain.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
}
