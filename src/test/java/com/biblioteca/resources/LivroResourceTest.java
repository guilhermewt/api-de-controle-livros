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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.biblioteca.entities.Livro;
import com.biblioteca.requests.LivroPostRequestBody;
import com.biblioteca.services.serviceLivro;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.LivroCreator;
import com.biblioteca.util.LivroPostRequestBodyCreator;
import com.biblioteca.util.LivroPutRequestBodyCreator;
import com.biblioteca.util.UsuarioCreator;

@ExtendWith(SpringExtension.class)
public class LivroResourceTest {
	
	@InjectMocks
	private LivroResources livroResource;
	
	@Mock
	private serviceLivro livroServiceMock;
	
	@Mock
	private GetUserDetails userAuthenticated;
	
	@BeforeEach
	void setUp() {
		PageImpl<Livro> livroPage = new PageImpl<>(List.of(LivroCreator.createValidLivro()));
		BDDMockito.when(livroServiceMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(livroPage);	
		
		BDDMockito.when(livroServiceMock.findAllNonPageable())
		.thenReturn(List.of(LivroCreator.createValidLivro()));
		
		BDDMockito.when(livroServiceMock.findByTitulo(ArgumentMatchers.anyString())).thenReturn(List.of(LivroCreator.createValidLivro()));
		
		BDDMockito.when(livroServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
		.thenReturn(LivroCreator.createValidLivro());
		
		BDDMockito.when(livroServiceMock.save(ArgumentMatchers.any(LivroPostRequestBody.class)))
		.thenReturn(LivroCreator.createValidLivro());
		
		BDDMockito.doNothing().when(livroServiceMock).delete(ArgumentMatchers.anyLong());
		
		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UsuarioCreator.createUserUsuario());
		
	}
	
	@Test
	@DisplayName("find all user books Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Livro livroSaved = LivroCreator.createValidLivro();
		
		Page<Livro> livro = this.livroResource.findAll(PageRequest.of(0, 1)).getBody();
		
		Assertions.assertThat(livro).isNotNull().isNotEmpty();
		Assertions.assertThat(livro.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(livro.toList().get(0)).isEqualTo(livroSaved);		
	}
	
	@Test
	@DisplayName("findAll_Return List of livro whenSuccessful")
	void findAll_ReturnListOfLivro_whenSuccessful() {
		Livro livroSaved = LivroCreator.createValidLivro();
		
		List<Livro> livro = this.livroResource.findAllNonPageable().getBody();
		
		Assertions.assertThat(livro).isNotNull().isNotEmpty();
		Assertions.assertThat(livro.get(0).getId()).isNotNull();
		Assertions.assertThat(livro.get(0)).isEqualTo(livroSaved);		
	}
	
	@Test
	@DisplayName("findByName Return List of livro whenSuccessful")
	void findByName_ReturnListoflivro_whenSuccessful() {
		Livro livroSaved = LivroCreator.createValidLivro();
		
		List<Livro> livro = this.livroResource.findByTitulo(livroSaved.getTitulo()).getBody();
		
		Assertions.assertThat(livro).isNotNull().isNotEmpty();
		Assertions.assertThat(livro.get(0).getId()).isNotNull();
		Assertions.assertThat(livro.get(0)).isEqualTo(livroSaved);		
	}
	
	@Test
	@DisplayName("findByName Return Empty List when No Livro Is Found")
	void findByName_ReturnEmptyListWhenNoLivroIsFound() {
		BDDMockito.when(livroServiceMock.findByTitulo(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<Livro> livro = this.livroResource.findByTitulo("fadf").getBody();
		
		Assertions.assertThat(livro).isNotNull().isEmpty();		
	}
	
	@Test
	@DisplayName("findById Return livro whenSuccessful")
	void findById_ReturnLivro_whenSuccessful() {
		Livro livroSaved = LivroCreator.createValidLivro();
		
		Livro livro = this.livroResource.findById(1).getBody();
		
		Assertions.assertThat(livro).isNotNull();
		Assertions.assertThat(livro.getId()).isNotNull();
		Assertions.assertThat(livro).isEqualTo(livroSaved);		
	}
	
	@Test
	@DisplayName("save Return livro whenSuccessful")
	void save_ReturnLivro_whenSuccessful() {
		Livro livroSaved = LivroCreator.createValidLivro();
		
		Livro livro = this.livroResource.save(LivroPostRequestBodyCreator.createLivroPostRequestBodyCreator()).getBody();
		
		Assertions.assertThat(livro).isNotNull();
		Assertions.assertThat(livro.getId()).isNotNull();
		Assertions.assertThat(livro).isEqualTo(livroSaved);		
	}
	
	@Test
	@DisplayName("delete removes livro whenSuccessful")
	void delete_removesLivro_whenSuccessful() {		
		ResponseEntity<Void> livro = this.livroResource.delete(1l);
		
		Assertions.assertThat(livro.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace livro whenSuccessful")
	void update_replaceLivro_whenSuccessful() {		
	    this.livroResource.save(LivroPostRequestBodyCreator.createLivroPostRequestBodyCreator()).getBody();
		
		ResponseEntity<Void> livro = this.livroResource.update(LivroPutRequestBodyCreator.createLivroPutRequestBodyCreator());
		
		Assertions.assertThat(livro.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);					
	}
	
}
