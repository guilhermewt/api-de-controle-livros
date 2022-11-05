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

import com.biblioteca.entities.Usuario;
import com.biblioteca.requests.UsuarioPostRequestBody;
import com.biblioteca.services.serviceUsuario;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.UsuarioCreator;
import com.biblioteca.util.UsuarioPostRequestBodyCreator;
import com.biblioteca.util.UsuarioPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class UsuarioResourceTest {
	//apenas arrumei os bbmock...
	@InjectMocks
	private  UsuarioResources userResource;

	@Mock
	private serviceUsuario userServiceMock;

	@Mock
	private GetUserDetails userAuthenticated;
	

	@BeforeEach
	void setUp() {
		PageImpl<Usuario> userPage = new PageImpl<>(List.of(UsuarioCreator.createAdminUsuario()));
		BDDMockito.when(userServiceMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(userPage);

		BDDMockito.when(userServiceMock.findAllNonPageable()).thenReturn(List.of(UsuarioCreator.createAdminUsuario()));

		BDDMockito.when(userServiceMock.findByName(ArgumentMatchers.anyString()))
				.thenReturn(List.of(UsuarioCreator.createAdminUsuario()));

		BDDMockito.when(userServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
				.thenReturn(UsuarioCreator.createAdminUsuario());
		
		BDDMockito.when(userServiceMock.getMyUser()).thenReturn(UsuarioCreator.createAdminUsuario());
		
		BDDMockito.when(userServiceMock.saveUser(ArgumentMatchers.any(UsuarioPostRequestBody.class))).thenReturn(UsuarioCreator.createUserUsuario());
		
		BDDMockito.when(userServiceMock.saveUserAdmin(ArgumentMatchers.any(UsuarioPostRequestBody.class))).thenReturn(UsuarioCreator.createAdminUsuario());

		BDDMockito.doNothing().when(userServiceMock).delete(ArgumentMatchers.anyLong());

		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UsuarioCreator.createAdminUsuario());

	}

	@Test
	@DisplayName("find all user books Return List Of Object Inside Page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		Page<Usuario> userPage = userResource.findAll(PageRequest.of(0, 1)).getBody();

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("find all user books Return List Of User whenSuccessful")
	void findAll_returnListOfUsers_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		List<Usuario> users = userResource.findAllNonPageable().getBody();
		Assertions.assertThat(users).isNotNull();
		Assertions.assertThat(users.get(0).getId()).isNotNull();
		Assertions.assertThat(users.get(0)).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("findByName Return List Of Livro whenSuccessful")
	void findByName_ReturnListOfUsers_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		List<Usuario> users = this.userResource.findByName(usuarioSaved.getName()).getBody();

		Assertions.assertThat(users).isNotNull().isNotEmpty();
		Assertions.assertThat(users.get(0).getId()).isNotNull();
		Assertions.assertThat(users.get(0)).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("findById return livro whenSuccessful")
	void findById_ReturnLivro_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		Usuario usuario = this.userResource.findById(usuarioSaved.getId()).getBody();

		Assertions.assertThat(usuario).isNotNull();
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("findUser authenticated return user whenSuccessful")
	void findUser_ReturnUser_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		Usuario usuario = this.userResource.getMyUser().getBody();

		Assertions.assertThat(usuario).isNotNull();
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("save user with ROLE_USER Return Livro whenSuccessful")
	void save_ReturnUser_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createUserUsuario();

		Usuario usuario = this.userResource.saveUser(UsuarioPostRequestBodyCreator.createUserPostRequestBodyCreator()).getBody();

		Assertions.assertThat(usuario).isNotNull();
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario).isEqualTo(usuarioSaved);
	}

	@Test
	@DisplayName("save user with ROLE_ADMIN Return Livro whenSuccessful")
	void save_ReturnUserAdmin_whenSuccessful() {
		Usuario usuarioSaved = UsuarioCreator.createAdminUsuario();

		Usuario usuario = this.userResource
				.saveUserAdmin(UsuarioPostRequestBodyCreator.createUserPostRequestBodyCreator()).getBody();

		Assertions.assertThat(usuario).isNotNull();
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario).isEqualTo(usuarioSaved);
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
						() -> this.userResource.update(UsuarioPutRequestBodyCreator.createUserPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}
}

