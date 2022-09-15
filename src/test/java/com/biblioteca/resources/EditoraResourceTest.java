package com.biblioteca.resources;

import java.util.Collections;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.biblioteca.entities.Editora;
import com.biblioteca.requests.EditoraPostRequestBody;
import com.biblioteca.services.serviceEditora;
import com.biblioteca.util.EditoraCreator;
import com.biblioteca.util.EditoraPostRequestBodyCreator;
import com.biblioteca.util.EditoraPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class EditoraResourceTest {
	
	@InjectMocks
	private EditoraResources editoraResource;
	
	@Mock
	private serviceEditora editoraServiceMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Editora> editoraPage = new PageImpl<>(List.of(EditoraCreator.createValidEditora()));
		BDDMockito.when(editoraServiceMock.findAll(ArgumentMatchers.any())).thenReturn(editoraPage);	
		
		BDDMockito.when(editoraServiceMock.findAllNonPageable())
		.thenReturn(List.of(EditoraCreator.createValidEditora()));
		
		BDDMockito.when(editoraServiceMock.findByName(ArgumentMatchers.anyString())).thenReturn(List.of(EditoraCreator.createValidEditora()));
		
		BDDMockito.when(editoraServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
		.thenReturn(EditoraCreator.createValidEditora());
		
		BDDMockito.when(editoraServiceMock.save(ArgumentMatchers.any(EditoraPostRequestBody.class)))
		.thenReturn(EditoraCreator.createValidEditora());
		
		BDDMockito.doNothing().when(editoraServiceMock).delete(ArgumentMatchers.anyLong());
		
	}
	
	@Test
	@DisplayName("findAll_Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Editora editoraSaved = EditoraCreator.createValidEditora();
		
		Page<Editora> editora = this.editoraResource.findAll(null).getBody();
		
		Assertions.assertThat(editora).isNotNull().isNotEmpty();
		Assertions.assertThat(editora.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(editora.toList().get(0)).isEqualTo(editoraSaved);		
	}
	
	@Test
	@DisplayName("findAll_Return List of editora whenSuccessful")
	void findAll_ReturnListOfEditora_whenSuccessful() {
		Editora editoraSaved = EditoraCreator.createValidEditora();
		
		List<Editora> editora = this.editoraResource.findAllNonPageable().getBody();
		
		Assertions.assertThat(editora).isNotNull().isNotEmpty();
		Assertions.assertThat(editora.get(0).getId()).isNotNull();
		Assertions.assertThat(editora.get(0)).isEqualTo(editoraSaved);		
	}
	
	@Test
	@DisplayName("findByName Return List of editora whenSuccessful")
	void findByName_ReturnListofeditora_whenSuccessful() {
		Editora editoraSaved = EditoraCreator.createValidEditora();
		
		List<Editora> editora = this.editoraResource.findByName(editoraSaved.getNome()).getBody();
		
		Assertions.assertThat(editora).isNotNull().isNotEmpty();
		Assertions.assertThat(editora.get(0).getId()).isNotNull();
		Assertions.assertThat(editora.get(0)).isEqualTo(editoraSaved);		
	}
	
	@Test
	@DisplayName("findByName Return Empty List when No Editora Is Found")
	void findByName_ReturnEmptyListWhenNoEditoraIsFound() {
		BDDMockito.when(editoraServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<Editora> editora = this.editoraResource.findByName("fadf").getBody();
		
		Assertions.assertThat(editora).isNotNull().isEmpty();		
	}
	
	@Test
	@DisplayName("findById Return editora whenSuccessful")
	void findById_ReturnEditora_whenSuccessful() {
		Editora editoraSaved = EditoraCreator.createValidEditora();
		
		Editora editora = this.editoraResource.findById(1).getBody();
		
		Assertions.assertThat(editora).isNotNull();
		Assertions.assertThat(editora.getId()).isNotNull();
		Assertions.assertThat(editora).isEqualTo(editoraSaved);		
	}
	
	@Test
	@DisplayName("save Return editora whenSuccessful")
	void save_ReturnEditora_whenSuccessful() {
		Editora editoraSaved = EditoraCreator.createValidEditora();
		
		Editora editora = this.editoraResource.save(EditoraPostRequestBodyCreator.createEditoraPostRequestBodyCreator()).getBody();
		
		Assertions.assertThat(editora).isNotNull();
		Assertions.assertThat(editora.getId()).isNotNull();
		Assertions.assertThat(editora).isEqualTo(editoraSaved);		
	}
	
	@Test
	@DisplayName("delete removes editora whenSuccessful")
	void delete_removesEditora_whenSuccessful() {		
		ResponseEntity<Void> editora = this.editoraResource.delete(1);
		
		Assertions.assertThat(editora.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace editora whenSuccessful")
	void update_replaceEditora_whenSuccessful() {		
	    this.editoraResource.save(EditoraPostRequestBodyCreator.createEditoraPostRequestBodyCreator()).getBody();
		
		ResponseEntity<Void> editora = this.editoraResource.update(EditoraPutRequestBodyCreator.createEditoraPutRequestBodyCreator());
		
		Assertions.assertThat(editora.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);					
	}
	
}
