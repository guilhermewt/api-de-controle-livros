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
@DisplayName("test for autor repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AutorRepositoryTests {
	
	@Autowired
	private RepositorioAutor reposityAutor;
	
	@Test
	@DisplayName("find all return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		
		Autor autorToBeSaved = this.reposityAutor.save(AutorCreator.createAutorToBeSaved());
		Page<Autor> autorSaved = this.reposityAutor.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(autorSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(autorSaved.toList().get(0)).isEqualTo(autorToBeSaved);
	}
	
	@Test
	@DisplayName("find all return list of autor whensuccessful")
	void findAll_returnListOfAutor_whenSuccessful() {
		
		Autor autorToBeSaved = this.reposityAutor.save(AutorCreator.createAutorToBeSaved());
		List<Autor> autorSaved = this.reposityAutor.findAll();
		
		Assertions.assertThat(autorSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(autorSaved.get(0)).isEqualTo(autorToBeSaved);
	}
	
	@Test
	@DisplayName("findById return autor whenSuccessful")
	void findByid_returnAutor_whenSuccessful() {
		Autor autorToBeSaved = this.reposityAutor.save(AutorCreator.createValidAutor());
		Autor autorSaved = this.reposityAutor.findById(autorToBeSaved.getId()).get();
		
		Assertions.assertThat(autorSaved).isNotNull();
		Assertions.assertThat(autorSaved.getId()).isNotNull();
		Assertions.assertThat(autorSaved).isEqualTo(autorToBeSaved);
	}
	
	@Test
	@DisplayName("findByName Return List Of Autor whenSuccessful")
	void findByName_ReturnListOfAutor_whenSuccessful() {
		Autor autorToBeSaved = this.reposityAutor.save(AutorCreator.createValidAutor());
		String name = autorToBeSaved.getNome();
		
		List<Autor> autorSaved = this.reposityAutor.findByNomeContainingIgnoreCase(name);
		
		Assertions.assertThat(autorSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(autorSaved.get(0).getId()).isNotNull();
		Assertions.assertThat(autorSaved.get(0)).isEqualTo(autorToBeSaved);
	}
	
	@Test
	@DisplayName("save return autor whenSuccessful")
	void save_returnAutor_whenSuccessful() {
		Autor autorToBeSaved = AutorCreator.createAutorToBeSaved();
		Autor autorSaved = this.reposityAutor.save(autorToBeSaved);
		
		Assertions.assertThat(autorSaved).isNotNull();
		Assertions.assertThat(autorSaved.getId()).isNotNull();
		Assertions.assertThat(autorSaved).isEqualTo(autorSaved);
	}
	
	@Test
	@DisplayName("delete removes autor whenSuccessful")
	void delete_removesAutor_whenSuccessful() {
		Autor autorSaved = this.reposityAutor.save(AutorCreator.createAutorToBeSaved());
		
	    this.reposityAutor.deleteById(autorSaved.getId());
	    
	    Optional<Autor> autorDeleted = this.reposityAutor.findById(autorSaved.getId());
	    
	    Assertions.assertThat(autorDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace autor whenSuccessful")
	void update_replaceAutor_whenSuccessful() {
		this.reposityAutor.save(AutorCreator.createAutorToBeSaved());
		
		Autor autorToBeUpdate = AutorCreator.createUpdatedAutor();
		
	    Autor autorUpdate = this.reposityAutor.save(autorToBeUpdate);
	    
	    Assertions.assertThat(autorUpdate).isNotNull();
	    Assertions.assertThat(autorUpdate.getId()).isNotNull();
	    Assertions.assertThat(autorUpdate).isEqualTo(autorToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when autor name is empty")
	void save_throwConstrationViolationException_whenAutorNameIsEmpty() {
		Autor autor = new Autor();
		
		Assertions.assertThatThrownBy(() -> this.reposityAutor.save(autor))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
