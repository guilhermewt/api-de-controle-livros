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

import com.biblioteca.entities.Autor;
import com.biblioteca.util.AutorCreator;

@DataJpaTest
@DisplayName("test for editora repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AutorRepositoryTests {
	
	@Autowired
	private RepositorioAutor reposityAutor;
	
	@Test
	@DisplayName("find all return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		
		Autor editoraToBeSaved = this.reposityAutor.save(AutorCreator.createAutorToBeSaved());
		Page<Autor> editoraSaved = this.reposityAutor.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(editoraSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(editoraSaved.toList().get(0)).isEqualTo(editoraToBeSaved);
	}
	
	@Test
	@DisplayName("find all return list of editora whensuccessful")
	void findAll_returnListOfAutor_whenSuccessful() {
		
		Autor editoraToBeSaved = this.reposityAutor.save(AutorCreator.createAutorToBeSaved());
		List<Autor> editoraSaved = this.reposityAutor.findAll();
		
		Assertions.assertThat(editoraSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(editoraSaved.get(0)).isEqualTo(editoraToBeSaved);
	}
	
	@Test
	@DisplayName("findById return editora whenSuccessful")
	void findByid_returnAutor_whenSuccessful() {
		Autor editoraToBeSaved = this.reposityAutor.save(AutorCreator.createValidAutor());
		Autor editoraSaved = this.reposityAutor.findById(editoraToBeSaved.getId()).get();
		
		Assertions.assertThat(editoraSaved).isNotNull();
		Assertions.assertThat(editoraSaved.getId()).isNotNull();
		Assertions.assertThat(editoraSaved).isEqualTo(editoraToBeSaved);
	}
	
	@Test
	@DisplayName("findByName Return List Of Autor whenSuccessful")
	void findByName_ReturnListOfAutor_whenSuccessful() {
		Autor editoraToBeSaved = this.reposityAutor.save(AutorCreator.createValidAutor());
		String name = editoraToBeSaved.getNome();
		
		List<Autor> editoraSaved = this.reposityAutor.findByNomeContainingIgnoreCase(name);
		
		Assertions.assertThat(editoraSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(editoraSaved.get(0).getId()).isNotNull();
		Assertions.assertThat(editoraSaved.get(0)).isEqualTo(editoraToBeSaved);
	}
	
	@Test
	@DisplayName("save return editora whenSuccessful")
	void save_returnAutor_whenSuccessful() {
		Autor editoraToBeSaved = AutorCreator.createAutorToBeSaved();
		Autor editoraSaved = this.reposityAutor.save(editoraToBeSaved);
		
		Assertions.assertThat(editoraSaved).isNotNull();
		Assertions.assertThat(editoraSaved.getId()).isNotNull();
		Assertions.assertThat(editoraSaved).isEqualTo(editoraSaved);
	}
	
	@Test
	@DisplayName("delete removes editora whenSuccessful")
	void delete_removesAutor_whenSuccessful() {
		Autor editoraSaved = this.reposityAutor.save(AutorCreator.createAutorToBeSaved());
		
	    this.reposityAutor.deleteById(editoraSaved.getId());
	    
	    Optional<Autor> editoraDeleted = this.reposityAutor.findById(editoraSaved.getId());
	    
	    Assertions.assertThat(editoraDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace editora whenSuccessful")
	void update_replaceAutor_whenSuccessful() {
		this.reposityAutor.save(AutorCreator.createAutorToBeSaved());
		
		Autor editoraToBeUpdate = AutorCreator.createUpdatedAutor();
		
	    Autor editoraUpdate = this.reposityAutor.save(editoraToBeUpdate);
	    
	    Assertions.assertThat(editoraUpdate).isNotNull();
	    Assertions.assertThat(editoraUpdate.getId()).isNotNull();
	    Assertions.assertThat(editoraUpdate).isEqualTo(editoraToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when editora name is empty")
	void save_throwConstrationViolationException_whenAutorNameIsEmpty() {
		Autor editora = new Autor();
		
		Assertions.assertThatThrownBy(() -> this.reposityAutor.save(editora))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
