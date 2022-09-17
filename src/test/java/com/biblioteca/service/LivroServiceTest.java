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

import com.biblioteca.entities.Livro;
import com.biblioteca.repository.RepositorioAutor;
import com.biblioteca.repository.RepositorioEditora;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.services.serviceLivro;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.util.AutorCreator;
import com.biblioteca.util.EditoraCreator;
import com.biblioteca.util.LivroCreator;
import com.biblioteca.util.LivroPostRequestBodyCreator;
import com.biblioteca.util.LivroPutRequestBodyCreator;
import com.biblioteca.util.UsuarioCreator;

@ExtendWith(SpringExtension.class)
public class LivroServiceTest {
	
	@InjectMocks
	private serviceLivro livroService;
	
	@Mock
	private RepositorioLivro livroRepositoryMock;
	
	@Mock
	private RepositorioAutor autorRepositoryMock;
	
	@Mock
	private RepositorioEditora editoraRepositoryMock;
	
	
	@Mock
	private RepositorioUsuario usuarioRepositoryMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Livro> livroPage = new PageImpl<>(List.of(LivroCreator.createValidLivro()));
		BDDMockito.when(livroRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(livroPage);
		
		BDDMockito.when(livroRepositoryMock.findAll()).thenReturn(List.of(LivroCreator.createValidLivro()));
		
		BDDMockito.when(livroRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(LivroCreator.createValidLivro()));
		
		BDDMockito.when(livroRepositoryMock.findByTituloContainingIgnoreCase(ArgumentMatchers.anyString()))
		.thenReturn(List.of(LivroCreator.createValidLivro()));
		
		BDDMockito.when(livroRepositoryMock.save(ArgumentMatchers.any(Livro.class))).thenReturn(LivroCreator.createValidLivro());
		
		BDDMockito.doNothing().when(livroRepositoryMock).delete(ArgumentMatchers.any(Livro.class));
	}
	
	@Test
	@DisplayName("findAll Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Livro livro = LivroCreator.createValidLivro();
		
		Page<Livro> livroPage = this.livroService.findAll(PageRequest.of(0, 1));
		
		Assertions.assertThat(livroPage).isNotNull().isNotEmpty();
		Assertions.assertThat(livroPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(livroPage.toList().get(0)).isEqualTo(livro);
	}
	
	@Test
	@DisplayName("findAll Return List Of Livro whenSuccessful")
	void findAll_ReturnListOfLivro_whenSuccessful() {
		Livro livroSaved = LivroCreator.createValidLivro();
		
		List<Livro> livro = this.livroService.findAllNonPageable();
		
		Assertions.assertThat(livro).isNotNull().isNotEmpty();
		Assertions.assertThat(livro.get(0).getId()).isNotNull();
		Assertions.assertThat(livro.get(0)).isEqualTo(livroSaved);
	}
	
	@Test
	@DisplayName("findById return livro whenSuccessful")
	void findById_ReturnLivro_whenSuccessful() {
		Livro livroSaved = LivroCreator.createValidLivro();
		
		Livro livro = this.livroService.findByIdOrElseThrowResourceNotFoundException(1);
		
		Assertions.assertThat(livro).isNotNull();
		Assertions.assertThat(livro.getId()).isNotNull();
		Assertions.assertThat(livro).isEqualTo(livroSaved);
	}
	
	@Test
	@DisplayName("findById Return Bad Request Exception When Livro Is Not Found")
	void findById_ReturnBadRequestExceptionWhenLivroIsNotFound() {
		BDDMockito.when(livroRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatCode(() -> this.livroService.findByIdOrElseThrowResourceNotFoundException(1))
		.isInstanceOf(BadRequestException.class);
	}
	
	@Test
	@DisplayName("findByName Return List Of Livro whenSuccessful")
	void findByName_ReturnListOfLivro_whenSuccessful() {
		Livro livroSaved = LivroCreator.createValidLivro();
		
		List<Livro> livro = this.livroService.findByTitulo(livroSaved.getTitulo());
		
		Assertions.assertThat(livro).isNotNull().isNotEmpty();
		Assertions.assertThat(livro.get(0).getId()).isNotNull();
		Assertions.assertThat(livro.get(0)).isEqualTo(livroSaved);	
	}
	
	@Test
	@DisplayName("findByName Return Empty List when no Livro is found")
	void findByName_ReturnEmptyListWhenNoLivroIsFound() {
		BDDMockito.when(livroRepositoryMock.findByTituloContainingIgnoreCase(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<Livro> livro = this.livroService.findByTitulo("xaxa");
		
		Assertions.assertThat(livro).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("save Return Livro whenSuccessful")
	void save_ReturnLivro_whenSuccessful() {
		BDDMockito.when(usuarioRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(UsuarioCreator.createAdminUsuario()));
		
		BDDMockito.when(autorRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(AutorCreator.createValidAutor()));
		
		BDDMockito.when(editoraRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(EditoraCreator.createValidEditora()));
		
		Livro livroSaved = LivroCreator.createValidLivro();
		
		Livro livro = this.livroService.save(LivroPostRequestBodyCreator.createLivroPostRequestBodyCreator(),1l,1l,1l);
		
		Assertions.assertThat(livro).isNotNull();
		Assertions.assertThat(livro.getId()).isNotNull();
		Assertions.assertThat(livro).isEqualTo(livroSaved);
	}
	
	@Test
	@DisplayName("delete Removes Livro whenSuccessful")
	void delete_RemovesLivro_whenSuccessful() {	
		Assertions.assertThatCode(() -> this.livroService.delete(1l)).doesNotThrowAnyException();
	}
	
	@Test
	@DisplayName("update Replace Livro whenSuccessful")
	void update_ReplaveLivro_whenSuccessful() {		
		Assertions.assertThatCode(() -> this.livroService.update(LivroPutRequestBodyCreator.createLivroPutRequestBodyCreator())).doesNotThrowAnyException();
	}
	
}
