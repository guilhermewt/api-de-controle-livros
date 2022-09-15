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

import com.biblioteca.entities.Editora;
import com.biblioteca.util.EditoraCreator;

@DataJpaTest
@DisplayName("test for editora repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EditoraRepositoryTest {
	
	@Autowired
	private RepositorioEditora repositoryEditora;
	
	@Test
	@DisplayName("find all return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		
		Editora editoraToBeSaved = this.repositoryEditora.save(EditoraCreator.createEditoraToBeSaved());
		Page<Editora> editoraSaved = this.repositoryEditora.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(editoraSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(editoraSaved.toList().get(0)).isEqualTo(editoraToBeSaved);
	}
	
	@Test
	@DisplayName("find all return list of editora whensuccessful")
	void findAll_returnListOfEditora_whenSuccessful() {
		
		Editora editoraToBeSaved = this.repositoryEditora.save(EditoraCreator.createEditoraToBeSaved());
		List<Editora> editoraSaved = this.repositoryEditora.findAll();
		
		Assertions.assertThat(editoraSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(editoraSaved.get(0)).isEqualTo(editoraToBeSaved);
	}
	
	@Test
	@DisplayName("findById return editora whenSuccessful")
	void findByid_returnEditora_whenSuccessful() {
		Editora editoraToBeSaved = this.repositoryEditora.save(EditoraCreator.createValidEditora());
		Editora editoraSaved = this.repositoryEditora.findById(editoraToBeSaved.getId()).get();
		
		Assertions.assertThat(editoraSaved).isNotNull();
		Assertions.assertThat(editoraSaved.getId()).isNotNull();
		Assertions.assertThat(editoraSaved).isEqualTo(editoraToBeSaved);
	}
	
	@Test
	@DisplayName("findByName Return List Of Editora whenSuccessful")
	void findByName_ReturnListOfEditora_whenSuccessful() {
		Editora editoraToBeSaved = this.repositoryEditora.save(EditoraCreator.createValidEditora());
		String name = editoraToBeSaved.getNome();
		
		List<Editora> editoraSaved = this.repositoryEditora.findByNomeContainingIgnoreCase(name);
		
		Assertions.assertThat(editoraSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(editoraSaved.get(0).getId()).isNotNull();
		Assertions.assertThat(editoraSaved.get(0)).isEqualTo(editoraToBeSaved);
	}
	
	@Test
	@DisplayName("save return editora whenSuccessful")
	void save_returnEditora_whenSuccessful() {
		Editora editoraToBeSaved = EditoraCreator.createEditoraToBeSaved();
		Editora editoraSaved = this.repositoryEditora.save(editoraToBeSaved);
		
		Assertions.assertThat(editoraSaved).isNotNull();
		Assertions.assertThat(editoraSaved.getId()).isNotNull();
		Assertions.assertThat(editoraSaved).isEqualTo(editoraSaved);
	}
	
	@Test
	@DisplayName("delete removes editora whenSuccessful")
	void delete_removesEditora_whenSuccessful() {
		Editora editoraSaved = this.repositoryEditora.save(EditoraCreator.createEditoraToBeSaved());
		
	    this.repositoryEditora.deleteById(editoraSaved.getId());
	    
	    Optional<Editora> editoraDeleted = this.repositoryEditora.findById(editoraSaved.getId());
	    
	    Assertions.assertThat(editoraDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace editora whenSuccessful")
	void update_replaceEditora_whenSuccessful() {
		this.repositoryEditora.save(EditoraCreator.createEditoraToBeSaved());
		
		Editora editoraToBeUpdate = EditoraCreator.createUpdatedEditora();
		
	    Editora editoraUpdate = this.repositoryEditora.save(editoraToBeUpdate);
	    
	    Assertions.assertThat(editoraUpdate).isNotNull();
	    Assertions.assertThat(editoraUpdate.getId()).isNotNull();
	    Assertions.assertThat(editoraUpdate).isEqualTo(editoraToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when editora name is empty")
	void save_throwConstrationViolationException_whenEditoraNameIsEmpty() {
		Editora editora = new Editora();
		
		Assertions.assertThatThrownBy(() -> this.repositoryEditora.save(editora))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
