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

import com.biblioteca.entities.Autor;
import com.biblioteca.repository.RepositorioAutor;
import com.biblioteca.services.serviceAutor;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.util.AutorCreator;
import com.biblioteca.util.AutorPostRequestBodyCreator;
import com.biblioteca.util.AutorPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class AutorServiceTest {
	
	@InjectMocks
	private serviceAutor autorService;
	
	@Mock
	private RepositorioAutor autorRepositoryMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Autor> autorPage = new PageImpl<>(List.of(AutorCreator.createValidAutor()));
		BDDMockito.when(autorRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(autorPage);
		
		BDDMockito.when(autorRepositoryMock.findAll()).thenReturn(List.of(AutorCreator.createValidAutor()));
		
		BDDMockito.when(autorRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(AutorCreator.createValidAutor()));
		
		BDDMockito.when(autorRepositoryMock.findByNomeContainingIgnoreCase(ArgumentMatchers.anyString()))
		.thenReturn(List.of(AutorCreator.createValidAutor()));
		
		BDDMockito.when(autorRepositoryMock.save(ArgumentMatchers.any(Autor.class))).thenReturn(AutorCreator.createValidAutor());
		
		BDDMockito.doNothing().when(autorRepositoryMock).delete(ArgumentMatchers.any(Autor.class));
	}
	
	@Test
	@DisplayName("findAll Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Autor autor = AutorCreator.createValidAutor();
		
		Page<Autor> autorPage = this.autorService.findAll(PageRequest.of(0, 1));
		
		Assertions.assertThat(autorPage).isNotNull().isNotEmpty();
		Assertions.assertThat(autorPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(autorPage.toList().get(0)).isEqualTo(autor);
	}
	
	@Test
	@DisplayName("findAll Return List Of Autor whenSuccessful")
	void findAll_ReturnListOfAutor_whenSuccessful() {
		Autor autorSaved = AutorCreator.createValidAutor();
		
		List<Autor> autor = this.autorService.findAllNonPageable();
		
		Assertions.assertThat(autor).isNotNull().isNotEmpty();
		Assertions.assertThat(autor.get(0).getId()).isNotNull();
		Assertions.assertThat(autor.get(0)).isEqualTo(autorSaved);
	}
	
	@Test
	@DisplayName("findById return autor whenSuccessful")
	void findById_ReturnAutor_whenSuccessful() {
		Autor autorSaved = AutorCreator.createValidAutor();
		
		Autor autor = this.autorService.findByIdOrElseThrowResourceNotFoundException(1);
		
		Assertions.assertThat(autor).isNotNull();
		Assertions.assertThat(autor.getId()).isNotNull();
		Assertions.assertThat(autor).isEqualTo(autorSaved);
	}
	
	@Test
	@DisplayName("findById Return Bad Request Exception When Autor Is Not Found")
	void findById_ReturnBadRequestExceptionWhenAutorIsNotFound() {
		BDDMockito.when(autorRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatCode(() -> this.autorService.findByIdOrElseThrowResourceNotFoundException(1))
		.isInstanceOf(BadRequestException.class);
	}
	
	@Test
	@DisplayName("findByName Return List Of Autor whenSuccessful")
	void findByName_ReturnListOfAutor_whenSuccessful() {
		Autor autorSaved = AutorCreator.createValidAutor();
		
		List<Autor> autor = this.autorService.findByName(autorSaved.getNome());
		
		Assertions.assertThat(autor).isNotNull().isNotEmpty();
		Assertions.assertThat(autor.get(0).getId()).isNotNull();
		Assertions.assertThat(autor.get(0)).isEqualTo(autorSaved);	
	}
	
	@Test
	@DisplayName("findByName Return Empty List when no Autor is found")
	void findByName_ReturnEmptyListWhenNoAutorIsFound() {
		BDDMockito.when(autorRepositoryMock.findByNomeContainingIgnoreCase(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<Autor> autor = this.autorService.findByName("xaxa");
		
		Assertions.assertThat(autor).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("save Return Autor whenSuccessful")
	void save_ReturnAutor_whenSuccessful() {
		Autor autorSaved = AutorCreator.createValidAutor();
		
		Autor autor = this.autorService.save(AutorPostRequestBodyCreator.createAutorPostRequestBodyCreator());
		
		Assertions.assertThat(autor).isNotNull();
		Assertions.assertThat(autor.getId()).isNotNull();
		Assertions.assertThat(autor).isEqualTo(autorSaved);
	}
	
	@Test
	@DisplayName("delete Removes Autor whenSuccessful")
	void delete_RemovesAutor_whenSuccessful() {	
		Assertions.assertThatCode(() -> this.autorService.delete(1l)).doesNotThrowAnyException();
	}
	
	@Test
	@DisplayName("update ReplaceAutor whenSuccessful")
	void update_ReplaveAutor_whenSuccessful() {		
		Assertions.assertThatCode(() -> this.autorService.update(AutorPutRequestBodyCreator.createAutorPutRequestBodyCreator())).doesNotThrowAnyException();
	}
	
}
