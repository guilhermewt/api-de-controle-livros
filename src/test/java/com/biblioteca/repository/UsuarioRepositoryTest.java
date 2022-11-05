package com.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import com.biblioteca.entities.Usuario;
import com.biblioteca.util.UsuarioCreator;

@DataJpaTest
@DisplayName("test for usuario repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsuarioRepositoryTest {
	
	@Autowired
	private RepositorioUsuario userRepository;
	
	@Test
	@DisplayName("find all user books by id return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Usuario usuarioToBeSaved = this.userRepository.save(UsuarioCreator.createAdminUsuario());
		Page<Usuario> usuarioSaved = this.userRepository.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(usuarioSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(usuarioSaved.toList().get(0)).isEqualTo(usuarioToBeSaved);
	}
	
	@Test
	@DisplayName("find all user books by id return list of usuario whensuccessful")
	void findAll_returnListOfUsuario_whenSuccessful() {
		Usuario usuarioToBeSaved = this.userRepository.save(UsuarioCreator.createAdminUsuario());
		List<Usuario> usuarioSaved = this.userRepository.findAll();
		
		Assertions.assertThat(usuarioSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(usuarioSaved.get(0)).isEqualTo(usuarioToBeSaved);
	}
	
	@Test
	@DisplayName("findById return usuario whenSuccessful")
	void findByid_returnUsuario_whenSuccessful() {		
		Usuario usuarioToBeSaved = this.userRepository.save(UsuarioCreator.createAdminUsuario());
		Usuario usuarioSaved = this.userRepository.findById(usuarioToBeSaved.getId()).get();
		
		Assertions.assertThat(usuarioSaved).isNotNull();
		Assertions.assertThat(usuarioSaved.getId()).isNotNull();
		Assertions.assertThat(usuarioSaved).isEqualTo(usuarioToBeSaved);
	}
		
	
	@Test
	@DisplayName("save return usuario whenSuccessful")
	void save_returnUsuario_whenSuccessful() {
		Usuario usuarioToBeSaved = UsuarioCreator.createAdminUsuario();
		Usuario usuarioSaved = this.userRepository.save(usuarioToBeSaved);
		
		Assertions.assertThat(usuarioSaved).isNotNull();
		Assertions.assertThat(usuarioSaved.getId()).isNotNull();
		Assertions.assertThat(usuarioSaved).isEqualTo(usuarioToBeSaved);
	}
	
	@Test
	@DisplayName("delete removes usuario whenSuccessful")
	void delete_removesUsuario_whenSuccessful() {
		Usuario usuario = this.userRepository.save(UsuarioCreator.createUserUsuario());
	
	    this.userRepository.delete(usuario);
	    
	    Optional<Usuario> usuarioDeleted = this.userRepository.findById(usuario.getId());
	    
	    Assertions.assertThat(usuarioDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace usuario whenSuccessful")
	void update_replaceUsuario_whenSuccessful() {
		this.userRepository.save(UsuarioCreator.createAdminUsuario());
			
		Usuario usuarioToBeUpdate = UsuarioCreator.createUserToBeUpdate();
	
	    Usuario usuarioUpdated = this.userRepository.save(usuarioToBeUpdate);
	    
	    Assertions.assertThat(usuarioUpdated).isNotNull();
	    Assertions.assertThat(usuarioUpdated.getId()).isNotNull();
	    Assertions.assertThat(usuarioUpdated).isEqualTo(usuarioToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when usuario name is empty")
	void save_throwConstrationViolationException_whenUsuarioNameIsEmpty() {
		Usuario usuario = new Usuario();
		
		Assertions.assertThatThrownBy(() -> this.userRepository.save(usuario))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}