package com.biblioteca.service;

import java.util.List;
import java.util.Optional;

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
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.repository.RoleModelRepository;
import com.biblioteca.services.UserDomainService;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UserDomainCreator;
import com.biblioteca.util.UserDomainPostRequestBodyCreator;
import com.biblioteca.util.UserDomainPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class UserDomainServiceTest {

	@InjectMocks
	private UserDomainService userService;

	@Mock
	private UserDomainRepository userRepositoryMock;
	
	@Mock
	private RoleModelRepository roleModelRepository;

	@Mock
	private GetUserDetails userAuthenticated;

	@BeforeEach
	void setUp() {
		PageImpl<UserDomain> userPage = new PageImpl<>(List.of(UserDomainCreator.createUserDomainWithRoleADMIN()));
		BDDMockito.when(userRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(userPage);

		BDDMockito.when(userRepositoryMock.findAll()).thenReturn(List.of(UserDomainCreator.createUserDomainWithRoleADMIN()));

		BDDMockito.when(userRepositoryMock.findBynameContainingIgnoreCase(ArgumentMatchers.anyString()))
				.thenReturn(List.of(UserDomainCreator.createUserDomainWithRoleADMIN()));

		BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(UserDomainCreator.createUserDomainWithRoleADMIN()));
		
		BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(UserDomain.class))).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());

		BDDMockito.doNothing().when(userRepositoryMock).delete(ArgumentMatchers.any(UserDomain.class));

		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UserDomainCreator.createUserDomainWithRoleADMIN());
		
		BDDMockito.when(roleModelRepository.findById(ArgumentMatchers.eq(1l))).thenReturn(Optional.of(RolesCreator.createAdminRoleModel()));
		BDDMockito.when(roleModelRepository.findById(ArgumentMatchers.eq(2l))).thenReturn(Optional.of(RolesCreator.createUserRoleModel()));

	}

	@Test
	@DisplayName("find all user books Return List Of Object Inside Page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		Page<UserDomain> userPage = userService.findAll(PageRequest.of(0, 1));

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("find all user books Return List Of User whenSuccessful")
	void findAll_returnListOfUsers_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		List<UserDomain> users = userService.findAllNonPageable();

		Assertions.assertThat(users).isNotNull();
		Assertions.assertThat(users.get(0).getId()).isNotNull();
		Assertions.assertThat(users.get(0)).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("findByName Return List Of Livro whenSuccessful")
	void findByName_ReturnListOfUsers_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		List<UserDomain> users = this.userService.findByName(userDomainSaved.getName());

		Assertions.assertThat(users).isNotNull().isNotEmpty();
		Assertions.assertThat(users.get(0).getId()).isNotNull();
		Assertions.assertThat(users.get(0)).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("findById return livro whenSuccessful")
	void findById_ReturnLivro_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		UserDomain userDomain = this.userService.findByIdOrElseThrowResourceNotFoundException(userDomainSaved.getId());

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("findUser authenticated return user whenSuccessful")
	void findUser_ReturnUser_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		UserDomain userDomain = this.userService.getMyUser();

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("save user with ROLE_USER Return Livro whenSuccessful")
	void save_ReturnUser_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		UserDomain userDomain = this.userService.saveUser(UserDomainPostRequestBodyCreator.createUserPostRequestBodyCreator());

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("save user with ROLE_ADMIN Return Livro whenSuccessful")
	void save_ReturnUserAdmin_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainWithRoleADMIN();

		UserDomain userDomain = this.userService
				.saveUserAdmin(UserDomainPostRequestBodyCreator.createUserPostRequestBodyCreator());

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("delete Removes Livro whenSuccessful")
	void delete_RemovesLivro_whenSuccessful() {
		Assertions.assertThatCode(() -> this.userService.delete(1l)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("update Replace Livro whenSuccessful")
	void update_ReplaveLivro_whenSuccessful() {
		Assertions
				.assertThatCode(
						() -> this.userService.update(UserDomainPutRequestBodyCreator.createUserDomainPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}

}
