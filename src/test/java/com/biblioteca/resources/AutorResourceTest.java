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

import com.biblioteca.entities.Autor;
import com.biblioteca.requests.AutorPostRequestBody;
import com.biblioteca.services.serviceAutor;
import com.biblioteca.util.AutorCreator;
import com.biblioteca.util.AutorPostRequestBodyCreator;
import com.biblioteca.util.AutorPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class AutorResourceTest {
	
	@InjectMocks
	private AutorResources autorResource;
	
	@Mock
	private serviceAutor autorServiceMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Autor> autorPage = new PageImpl<>(List.of(AutorCreator.createValidAutor()));
		BDDMockito.when(autorServiceMock.findAll(ArgumentMatchers.any())).thenReturn(autorPage);	
		
		BDDMockito.when(autorServiceMock.findAllNonPageable())
		.thenReturn(List.of(AutorCreator.createValidAutor()));
		
		BDDMockito.when(autorServiceMock.findByName(ArgumentMatchers.anyString())).thenReturn(List.of(AutorCreator.createValidAutor()));
		
		BDDMockito.when(autorServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
		.thenReturn(AutorCreator.createValidAutor());
		
		BDDMockito.when(autorServiceMock.save(ArgumentMatchers.any(AutorPostRequestBody.class)))
		.thenReturn(AutorCreator.createValidAutor());
		
		BDDMockito.doNothing().when(autorServiceMock).delete(ArgumentMatchers.anyLong());
		
	}
	
	@Test
	@DisplayName("findAll_Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Autor autorSaved = AutorCreator.createValidAutor();
		
		Page<Autor> autor = this.autorResource.findAll(null).getBody();
		
		Assertions.assertThat(autor).isNotNull().isNotEmpty();
		Assertions.assertThat(autor.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(autor.toList().get(0)).isEqualTo(autorSaved);		
	}
	
	@Test
	@DisplayName("findAll_Return List of autor whenSuccessful")
	void findAll_ReturnListOfAutor_whenSuccessful() {
		Autor autorSaved = AutorCreator.createValidAutor();
		
		List<Autor> autor = this.autorResource.findAllNonPageable().getBody();
		
		Assertions.assertThat(autor).isNotNull().isNotEmpty();
		Assertions.assertThat(autor.get(0).getId()).isNotNull();
		Assertions.assertThat(autor.get(0)).isEqualTo(autorSaved);		
	}
	
	@Test
	@DisplayName("findByName Return List of autor whenSuccessful")
	void findByName_ReturnListofautor_whenSuccessful() {
		Autor autorSaved = AutorCreator.createValidAutor();
		
		List<Autor> autor = this.autorResource.findByName(autorSaved.getNome()).getBody();
		
		Assertions.assertThat(autor).isNotNull().isNotEmpty();
		Assertions.assertThat(autor.get(0).getId()).isNotNull();
		Assertions.assertThat(autor.get(0)).isEqualTo(autorSaved);		
	}
	
	@Test
	@DisplayName("findByName Return Empty List when No Autor Is Found")
	void findByName_ReturnEmptyListWhenNoAutorIsFound() {
		BDDMockito.when(autorServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<Autor> autor = this.autorResource.findByName("fadf").getBody();
		
		Assertions.assertThat(autor).isNotNull().isEmpty();		
	}
	
	@Test
	@DisplayName("findById Return autor whenSuccessful")
	void findById_ReturnAutor_whenSuccessful() {
		Autor autorSaved = AutorCreator.createValidAutor();
		
		Autor autor = this.autorResource.findById(1).getBody();
		
		Assertions.assertThat(autor).isNotNull();
		Assertions.assertThat(autor.getId()).isNotNull();
		Assertions.assertThat(autor).isEqualTo(autorSaved);		
	}
	
	@Test
	@DisplayName("save Return autor whenSuccessful")
	void save_ReturnAutor_whenSuccessful() {
		Autor autorSaved = AutorCreator.createValidAutor();
		
		Autor autor = this.autorResource.save(AutorPostRequestBodyCreator.createAutorPostRequestBodyCreator()).getBody();
		
		Assertions.assertThat(autor).isNotNull();
		Assertions.assertThat(autor.getId()).isNotNull();
		Assertions.assertThat(autor).isEqualTo(autorSaved);		
	}
	
	@Test
	@DisplayName("delete removes autor whenSuccessful")
	void delete_removesAutor_whenSuccessful() {		
		ResponseEntity<Void> autor = this.autorResource.delete(1);
		
		Assertions.assertThat(autor.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace autor whenSuccessful")
	void update_replaceAutor_whenSuccessful() {		
	    this.autorResource.save(AutorPostRequestBodyCreator.createAutorPostRequestBodyCreator()).getBody();
		
		ResponseEntity<Void> autor = this.autorResource.update(AutorPutRequestBodyCreator.createAutorPutRequestBodyCreator());
		
		Assertions.assertThat(autor.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);					
	}
	
}
