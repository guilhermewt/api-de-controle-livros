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

import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.services.serviceUsuario;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.UsuarioCreator;
import com.biblioteca.util.UsuarioPostRequestBodyCreator;
import com.biblioteca.util.UsuarioPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class UsuarioServiceTest {

	@InjectMocks
	private serviceUsuario userService;

	@Mock
	private RepositorioUsuario userRepositoryMock;

	@Mock
	private GetUserDetails userAuthenticated;

	@BeforeEach
	void setUp() {
		PageImpl<Usuario> userPage = new PageImpl<>(List.of(UsuarioCreator.createAdminUsuario()));
		BDDMockito.when(userRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(userPage);

		BDDMockito.when(userRepositoryMock.findAll()).thenReturn(List.of(UsuarioCreator.createAdminUsuario()));

		BDDMockito.when(userRepositoryMock.findBynameContainingIgnoreCase(ArgumentMatchers.anyString()))
				.thenReturn(List.of(UsuarioCreator.createAdminUsuario()));

		BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(UsuarioCreator.createAdminUsuario()));
		
		BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(Usuario.class))).thenReturn(UsuarioCreator.createAdminUsuario());

		BDDMockito.doNothing().when(userRepositoryMock).delete(ArgumentMatchers.any(Usuario.class));

		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UsuarioCreator.createAdminUsuario());

	}

	@Test
	@DisplayName("find all user books Return List Of Object Inside Page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		Page<Usuario> userPage = userService.findAll(PageRequest.of(0, 1));

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("find all user books Return List Of User whenSuccessful")
	void findAll_returnListOfUsers_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		List<Usuario> users = userService.findAllNonPageable();

		Assertions.assertThat(users).isNotNull();
		Assertions.assertThat(users.get(0).getId()).isNotNull();
		Assertions.assertThat(users.get(0)).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("findByName Return List Of Livro whenSuccessful")
	void findByName_ReturnListOfUsers_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		List<Usuario> users = this.userService.findByName(usuarioSaved.getName());

		Assertions.assertThat(users).isNotNull().isNotEmpty();
		Assertions.assertThat(users.get(0).getId()).isNotNull();
		Assertions.assertThat(users.get(0)).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("findById return livro whenSuccessful")
	void findById_ReturnLivro_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		Usuario usuario = this.userService.findByIdOrElseThrowResourceNotFoundException(usuarioSaved.getId());

		Assertions.assertThat(usuario).isNotNull();
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("findUser authenticated return user whenSuccessful")
	void findUser_ReturnUser_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		Usuario usuario = this.userService.getMyUser();

		Assertions.assertThat(usuario).isNotNull();
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("save user with ROLE_USER Return Livro whenSuccessful")
	void save_ReturnUser_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		Usuario usuario = this.userService.saveUser(UsuarioPostRequestBodyCreator.createUserPostRequestBodyCreator());

		Assertions.assertThat(usuario).isNotNull();
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("save user with ROLE_ADMIN Return Livro whenSuccessful")
	void save_ReturnUserAdmin_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		Usuario usuario = this.userService
				.saveUserAdmin(UsuarioPostRequestBodyCreator.createUserPostRequestBodyCreator());

		Assertions.assertThat(usuario).isNotNull();
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario).isEqualTo(usuarioSaved);
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
						() -> this.userService.update(UsuarioPutRequestBodyCreator.createUserPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}

}
