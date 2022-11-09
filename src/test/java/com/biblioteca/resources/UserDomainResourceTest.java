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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.biblioteca.entities.UserDomain;
import com.biblioteca.requests.UserDomainPostRequestBody;
import com.biblioteca.services.UserDomainService;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.UserDomainCreator;
import com.biblioteca.util.UserDomainPostRequestBodyCreator;
import com.biblioteca.util.UserDomainPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class UserDomainResourceTest {
	
	@InjectMocks
	private  UserDomainResources userResource;

	@Mock
	private UserDomainService userServiceMock;

	@Mock
	private GetUserDetails userAuthenticated;
	
	@BeforeEach
	void setUp() {
		PageImpl<UserDomain> userPage = new PageImpl<>(List.of(UserDomainCreator.createUserDomainWithRoleADMIN()));
		BDDMockito.when(userServiceMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(userPage);

		BDDMockito.when(userServiceMock.findAllNonPageable()).thenReturn(List.of(UserDomainCreator.createUserDomainWithRoleADMIN()));

		BDDMockito.when(userServiceMock.findByName(ArgumentMatchers.anyString()))
				.thenReturn(List.of(UserDomainCreator.createUserDomainWithRoleADMIN()));

		BDDMockito.when(userServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
				.thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		BDDMockito.when(userServiceMock.getMyUser()).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		BDDMockito.when(userServiceMock.saveUser(ArgumentMatchers.any(UserDomainPostRequestBody.class))).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		BDDMockito.when(userServiceMock.saveUserAdmin(ArgumentMatchers.any(UserDomainPostRequestBody.class))).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());

		BDDMockito.doNothing().when(userServiceMock).delete(ArgumentMatchers.anyLong());

		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());

	}

	@Test
	@DisplayName("find all user books Return List Of Object Inside Page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		Page<UserDomain> userPage = userResource.findAll(PageRequest.of(0, 1)).getBody();

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("find all user books Return List Of User whenSuccessful")
	void findAll_returnListOfUsers_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		List<UserDomain> users = userResource.findAllNonPageable().getBody();
		Assertions.assertThat(users).isNotNull();
		Assertions.assertThat(users.get(0).getId()).isNotNull();
		Assertions.assertThat(users.get(0)).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("findByName Return List Of Livro whenSuccessful")
	void findByName_ReturnListOfUsers_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		List<UserDomain> users = this.userResource.findByName(userDomainSaved.getName()).getBody();

		Assertions.assertThat(users).isNotNull().isNotEmpty();
		Assertions.assertThat(users.get(0).getId()).isNotNull();
		Assertions.assertThat(users.get(0)).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("findById return livro whenSuccessful")
	void findById_ReturnLivro_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		UserDomain userDomain = this.userResource.findById(userDomainSaved.getId()).getBody();

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("findUser authenticated return user whenSuccessful")
	void findUser_ReturnUser_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		UserDomain userDomain = this.userResource.getMyUser().getBody();

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("save user with ROLE_USER Return Livro whenSuccessful")
	void save_ReturnUser_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		UserDomain userDomain = this.userResource.saveUser(UserDomainPostRequestBodyCreator.createUserPostRequestBodyCreator()).getBody();

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("save user with ROLE_ADMIN Return Livro whenSuccessful")
	void save_ReturnUserAdmin_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		UserDomain userDomain = this.userResource
				.saveUserAdmin(UserDomainPostRequestBodyCreator.createUserPostRequestBodyCreator()).getBody();

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("delete Removes Livro whenSuccessful")
	void delete_RemovesLivro_whenSuccessful() {
		Assertions.assertThatCode(() -> this.userResource.delete(1l)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("update Replace Livro whenSuccessful")
	void update_ReplaveLivro_whenSuccessful() {
		Assertions
				.assertThatCode(
						() -> this.userResource.update(UserDomainPutRequestBodyCreator.createUserDomainPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}
}

