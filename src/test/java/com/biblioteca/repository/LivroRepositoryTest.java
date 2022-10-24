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

import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.util.LivroCreator;
import com.biblioteca.util.UsuarioCreator;

@DataJpaTest
@DisplayName("test for livro repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class LivroRepositoryTest {
	
	@Autowired
	private RepositorioLivro repositoryLivro;
	
	@Autowired
	private RepositorioUsuario userRepository;
	
	@Test
	@DisplayName("find all user books return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Usuario usuario = this.userRepository.save(UsuarioCreator.createUserUsuario());
		
		Livro livroToBeSaved = this.repositoryLivro.save(LivroCreator.createValidLivro());
		Page<Livro> livroSaved = this.repositoryLivro.findByUsuarioId(usuario.getId(), PageRequest.of(0, 5));
		
		Assertions.assertThat(livroSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(livroSaved.toList().get(0)).isEqualTo(livroToBeSaved);
	}
	
	@Test
	@DisplayName("find all user books by id return list of livro whensuccessful")
	void findAll_returnListOfLivro_whenSuccessful() {
		Usuario usuario = this.userRepository.save(UsuarioCreator.createUserUsuario());
		
		Livro livroToBeSaved = this.repositoryLivro.save(LivroCreator.createValidLivro());
		List<Livro> livroSaved = this.repositoryLivro.findByUsuarioId(usuario.getId());
		
		Assertions.assertThat(livroSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(livroSaved.get(0)).isEqualTo(livroToBeSaved);
	}
	
	@Test
	@DisplayName("findById return livro whenSuccessful")
	void findByid_returnLivro_whenSuccessful() {
		Usuario usuario = this.userRepository.save(UsuarioCreator.createUserUsuario());
		
		Livro livroToBeSaved = this.repositoryLivro.save(LivroCreator.createValidLivro());
		Livro livroSaved = this.repositoryLivro.findAuthenticatedUserBooksById(livroToBeSaved.getId(), usuario.getId()).get();
		
		Assertions.assertThat(livroSaved).isNotNull();
		Assertions.assertThat(livroSaved.getId()).isNotNull();
		Assertions.assertThat(livroSaved).isEqualTo(livroToBeSaved);
	}
	
	@Test
	@DisplayName("findAuthenticatedUserBooksByTitle Return List Of Livro whenSuccessful")
	void findAuthenticatedUserBooksByTitle_ReturnListOfLivro_whenSuccessful() {
	    this.userRepository.save(UsuarioCreator.createUserUsuario());
		
		Livro livroToBeSaved = this.repositoryLivro.save(LivroCreator.createValidLivro());
		
		String titulo = livroToBeSaved.getTitulo();
		
		List<Livro> livroSaved = this.repositoryLivro.findAuthenticatedUserBooksByTitle(titulo, LivroCreator.createValidLivro().getUsuario().getId());
		
		Assertions.assertThat(livroSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(livroSaved.get(0).getId()).isNotNull();
		Assertions.assertThat(livroSaved.get(0)).isEqualTo(livroToBeSaved);
	}
	
	@Test
	@DisplayName("save return livro whenSuccessful")
	void save_returnLivro_whenSuccessful() {
		this.userRepository.save(UsuarioCreator.createUserUsuario());
		
		Livro livroToBeSaved = LivroCreator.createValidLivro();
		Livro livroSaved = this.repositoryLivro.save(livroToBeSaved);
		
		Assertions.assertThat(livroSaved).isNotNull();
		Assertions.assertThat(livroSaved.getId()).isNotNull();
		Assertions.assertThat(livroSaved).isEqualTo(livroSaved);
	}
	
	@Test
	@DisplayName("delete removes livro whenSuccessful")
	void delete_removesLivro_whenSuccessful() {
		Usuario usuario = this.userRepository.save(UsuarioCreator.createUserUsuario());
		Livro livroSaved = this.repositoryLivro.save(LivroCreator.createValidLivro());
		
	    this.repositoryLivro.deleteAuthenticatedUserBookById(livroSaved.getId(),livroSaved.getUsuario().getId());
	    
	    Optional<Livro> livroDeleted = this.repositoryLivro.findAuthenticatedUserBooksById(livroSaved.getId(), usuario.getId());
	    
	    Assertions.assertThat(livroDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace livro whenSuccessful")
	void update_replaceLivro_whenSuccessful() {
	    this.userRepository.save(UsuarioCreator.createUserUsuario());
		
		this.repositoryLivro.save(LivroCreator.createValidLivro());
		
		Livro livroToBeUpdate = LivroCreator.createUpdatedLivro();
		
	    Livro livroUpdate = this.repositoryLivro.save(livroToBeUpdate);
	    
	    Assertions.assertThat(livroUpdate).isNotNull();
	    Assertions.assertThat(livroUpdate.getId()).isNotNull();
	    Assertions.assertThat(livroUpdate).isEqualTo(livroToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when livro name is empty")
	void save_throwConstrationViolationException_whenLivroNameIsEmpty() {
		Livro livro = new Livro();
		
		Assertions.assertThatThrownBy(() -> this.repositoryLivro.save(livro))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
