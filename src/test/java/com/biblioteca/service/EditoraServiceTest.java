package com.biblioteca.service;

import java.util.Collections;
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

import com.biblioteca.entities.Editora;
import com.biblioteca.repository.RepositorioEditora;
import com.biblioteca.services.serviceEditora;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.util.EditoraCreator;
import com.biblioteca.util.EditoraPostRequestBodyCreator;
import com.biblioteca.util.EditoraPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class EditoraServiceTest {
	
	@InjectMocks
	private serviceEditora editoraService;
	
	@Mock
	private RepositorioEditora editoraRepositoryMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Editora> editoraPage = new PageImpl<>(List.of(EditoraCreator.createValidEditora()));
		BDDMockito.when(editoraRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(editoraPage);
		
		BDDMockito.when(editoraRepositoryMock.findAll()).thenReturn(List.of(EditoraCreator.createValidEditora()));
		
		BDDMockito.when(editoraRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(EditoraCreator.createValidEditora()));
		
		BDDMockito.when(editoraRepositoryMock.findByNomeContainingIgnoreCase(ArgumentMatchers.anyString()))
		.thenReturn(List.of(EditoraCreator.createValidEditora()));
		
		BDDMockito.when(editoraRepositoryMock.save(ArgumentMatchers.any(Editora.class))).thenReturn(EditoraCreator.createValidEditora());
		
		BDDMockito.doNothing().when(editoraRepositoryMock).delete(ArgumentMatchers.any(Editora.class));
	}
	
	@Test
	@DisplayName("findAll Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Editora editora = EditoraCreator.createValidEditora();
		
		Page<Editora> editoraPage = this.editoraService.findAll(PageRequest.of(0, 1));
		
		Assertions.assertThat(editoraPage).isNotNull().isNotEmpty();
		Assertions.assertThat(editoraPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(editoraPage.toList().get(0)).isEqualTo(editora);
	}
	
	@Test
	@DisplayName("findAll Return List Of Editora whenSuccessful")
	void findAll_ReturnListOfEditora_whenSuccessful() {
		Editora editoraSaved = EditoraCreator.createValidEditora();
		
		List<Editora> editora = this.editoraService.findAllNonPageable();
		
		Assertions.assertThat(editora).isNotNull().isNotEmpty();
		Assertions.assertThat(editora.get(0).getId()).isNotNull();
		Assertions.assertThat(editora.get(0)).isEqualTo(editoraSaved);
	}
	
	@Test
	@DisplayName("findById return editora whenSuccessful")
	void findById_ReturnEditora_whenSuccessful() {
		Editora editoraSaved = EditoraCreator.createValidEditora();
		
		Editora editora = this.editoraService.findByIdOrElseThrowResourceNotFoundException(1);
		
		Assertions.assertThat(editora).isNotNull();
		Assertions.assertThat(editora.getId()).isNotNull();
		Assertions.assertThat(editora).isEqualTo(editoraSaved);
	}
	
	@Test
	@DisplayName("findById Return Bad Request Exception When Editora Is Not Found")
	void findById_ReturnBadRequestExceptionWhenEditoraIsNotFound() {
		BDDMockito.when(editoraRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatCode(() -> this.editoraService.findByIdOrElseThrowResourceNotFoundException(1))
		.isInstanceOf(BadRequestException.class);
	}
	
	@Test
	@DisplayName("findByName Return List Of Editora whenSuccessful")
	void findByName_ReturnListOfEditora_whenSuccessful() {
		Editora editoraSaved = EditoraCreator.createValidEditora();
		
		List<Editora> editora = this.editoraService.findByName(editoraSaved.getNome());
		
		Assertions.assertThat(editora).isNotNull().isNotEmpty();
		Assertions.assertThat(editora.get(0).getId()).isNotNull();
		Assertions.assertThat(editora.get(0)).isEqualTo(editoraSaved);	
	}
	
	@Test
	@DisplayName("findByName Return Empty List when no Editora is found")
	void findByName_ReturnEmptyListWhenNoEditoraIsFound() {
		BDDMockito.when(editoraRepositoryMock.findByNomeContainingIgnoreCase(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<Editora> editora = this.editoraService.findByName("xaxa");
		
		Assertions.assertThat(editora).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("save Return Editora whenSuccessful")
	void save_ReturnEditora_whenSuccessful() {
		Editora editoraSaved = EditoraCreator.createValidEditora();
		
		Editora editora = this.editoraService.save(EditoraPostRequestBodyCreator.createEditoraPostRequestBodyCreator());
		
		Assertions.assertThat(editora).isNotNull();
		Assertions.assertThat(editora.getId()).isNotNull();
		Assertions.assertThat(editora).isEqualTo(editoraSaved);
	}
	
	@Test
	@DisplayName("delete Removes Editora whenSuccessful")
	void delete_RemovesEditora_whenSuccessful() {	
		Assertions.assertThatCode(() -> this.editoraService.delete(1l)).doesNotThrowAnyException();
	}
	
	@Test
	@DisplayName("update Replace Editora whenSuccessful")
	void update_ReplaveEditora_whenSuccessful() {		
		Assertions.assertThatCode(() -> this.editoraService.update(EditoraPutRequestBodyCreator.createEditoraPutRequestBodyCreator())).doesNotThrowAnyException();
	}
	
}
