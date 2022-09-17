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
import com.biblioteca.util.LivroCreator;

@DataJpaTest
@DisplayName("test for livro repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LivroRepositoryTest {
	
	@Autowired
	private RepositorioLivro repositoryLivro;
	
	@Test
	@DisplayName("find all return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		
		Livro livroToBeSaved = this.repositoryLivro.save(LivroCreator.createLivroToBeSaved());
		Page<Livro> livroSaved = this.repositoryLivro.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(livroSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(livroSaved.toList().get(0)).isEqualTo(livroToBeSaved);
	}
	
	@Test
	@DisplayName("find all return list of livro whensuccessful")
	void findAll_returnListOfLivro_whenSuccessful() {
		
		Livro livroToBeSaved = this.repositoryLivro.save(LivroCreator.createLivroToBeSaved());
		List<Livro> livroSaved = this.repositoryLivro.findAll();
		
		Assertions.assertThat(livroSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(livroSaved.get(0)).isEqualTo(livroToBeSaved);
	}
	
	@Test
	@DisplayName("findById return livro whenSuccessful")
	void findByid_returnLivro_whenSuccessful() {
		Livro livroToBeSaved = this.repositoryLivro.save(LivroCreator.createValidLivro());
		Livro livroSaved = this.repositoryLivro.findById(livroToBeSaved.getId()).get();
		
		Assertions.assertThat(livroSaved).isNotNull();
		Assertions.assertThat(livroSaved.getId()).isNotNull();
		Assertions.assertThat(livroSaved).isEqualTo(livroToBeSaved);
	}
	
	@Test
	@DisplayName("findByName Return List Of Livro whenSuccessful")
	void findByName_ReturnListOfLivro_whenSuccessful() {
		Livro livroToBeSaved = this.repositoryLivro.save(LivroCreator.createValidLivro());
		String titulo = livroToBeSaved.getTitulo();
		
		List<Livro> livroSaved = this.repositoryLivro.findByTituloContainingIgnoreCase(titulo);
		
		Assertions.assertThat(livroSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(livroSaved.get(0).getId()).isNotNull();
		Assertions.assertThat(livroSaved.get(0)).isEqualTo(livroToBeSaved);
	}
	
	@Test
	@DisplayName("save return livro whenSuccessful")
	void save_returnLivro_whenSuccessful() {
		Livro livroToBeSaved = LivroCreator.createLivroToBeSaved();
		Livro livroSaved = this.repositoryLivro.save(livroToBeSaved);
		
		Assertions.assertThat(livroSaved).isNotNull();
		Assertions.assertThat(livroSaved.getId()).isNotNull();
		Assertions.assertThat(livroSaved).isEqualTo(livroSaved);
	}
	
	@Test
	@DisplayName("delete removes livro whenSuccessful")
	void delete_removesLivro_whenSuccessful() {
		Livro livroSaved = this.repositoryLivro.save(LivroCreator.createLivroToBeSaved());
		
	    this.repositoryLivro.deleteById(livroSaved.getId());
	    
	    Optional<Livro> livroDeleted = this.repositoryLivro.findById(livroSaved.getId());
	    
	    Assertions.assertThat(livroDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace livro whenSuccessful")
	void update_replaceLivro_whenSuccessful() {
		this.repositoryLivro.save(LivroCreator.createLivroToBeSaved());
		
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
