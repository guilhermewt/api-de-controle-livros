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
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.biblioteca.data.JwtObject;
import com.biblioteca.entities.RoleModel;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.repository.GenrerRepository;
import com.biblioteca.repository.RoleModelRepository;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.requests.GenrerGetRequestBody;
import com.biblioteca.requests.LoginGetRequestBody;
import com.biblioteca.util.BookGetRequestBodyCreator;
import com.biblioteca.util.GenrerCreator;
import com.biblioteca.util.GenrerGetRequestBodyCreator;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UserDomainCreator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class GenrerResourceIT {

	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	private UserDomainRepository userDomainRepository;
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	@Autowired
	private GenrerRepository genrerRepository;
	
	private static RoleModel RoleADMIN = RolesCreator.createAdminRoleModel();
	private static RoleModel RoleUSER = RolesCreator.createUserRoleModel();
	
	private static UserDomain USER = UserDomainCreator.createUserDomainWithRoleUSER();
	
	@TestConfiguration
	@Lazy
	static class Config {
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdmin(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}
		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateRoleUser(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	public HttpHeaders httpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();		
		httpHeaders.add("Authorization", "Bearer " + login().getToken());		
		return httpHeaders;
	}
	
	public JwtObject login() {
		LoginGetRequestBody login = new LoginGetRequestBody("userBiblioteca","biblioteca");
		ResponseEntity<JwtObject> jwt = testRestTemplateRoleUser.postForEntity("/login", login, JwtObject.class);
		return jwt.getBody();
	}
	

	@Test
	@DisplayName("findAll Return List Of Genrers whenSuccessful")
	void findAll_ReturnLisGenrer_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RoleADMIN,RoleUSER));
		userDomainRepository.save(USER);
		genrerRepository.saveAll(GenrerCreator.createValidGenrer());

		
		List<GenrerGetRequestBody> genrer = testRestTemplateRoleUser.exchange("/genrers/all", HttpMethod.GET, new HttpEntity<>(httpHeaders()), 
				 new ParameterizedTypeReference<List<GenrerGetRequestBody>>() {
				}).getBody();
		
		Assertions.assertThat(genrer).isNotNull().isNotEmpty();
		Assertions.assertThat(genrer.get(0).getId()).isNotNull();
		Assertions.assertThat(genrer.get(0)).isEqualTo(GenrerGetRequestBodyCreator.createGenrerGetRequestBodyCreator());
	}
	
}
