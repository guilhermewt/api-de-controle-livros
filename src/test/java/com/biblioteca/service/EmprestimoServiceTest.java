package com.biblioteca.service;

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

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.repository.RepositorioEmprestimo;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.services.serviceEmprestimo;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;
import com.biblioteca.util.EmprestimoCreator;
import com.biblioteca.util.EmprestimoPostRequestBodyCreator;
import com.biblioteca.util.EmprestimoPutRequestBodyCreator;
import com.biblioteca.util.LivroCreator;
import com.biblioteca.util.UsuarioCreator;

@ExtendWith(SpringExtension.class)
public class EmprestimoServiceTest {
	
	@InjectMocks
	private serviceEmprestimo emprestimoService;
	
	@Mock
	private RepositorioEmprestimo emprestimoRepositoryMock;
	
	@Mock
	private RepositorioLivro livroRepositoryMock;
	
	@Mock
	private RepositorioUsuario usuarioRepositoryMock;
	
	@Mock
	private GetUserDetails userAuthenticated;
	
	@BeforeEach
	void setUp() {
		PageImpl<Emprestimo> emprestimoPage = new PageImpl<>(List.of(EmprestimoCreator.createValidEmprestimo()));
		BDDMockito.when(emprestimoRepositoryMock.findByUsuarioId(ArgumentMatchers.anyLong(),ArgumentMatchers.any(PageRequest.class)))
		.thenReturn(emprestimoPage);
		
		BDDMockito.when(emprestimoRepositoryMock.findByUsuarioId(ArgumentMatchers.anyLong())).thenReturn(List.of(EmprestimoCreator.createValidEmprestimo()));
		
		BDDMockito.when(emprestimoRepositoryMock.findAuthenticatedUserById(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(EmprestimoCreator.createValidEmprestimo()));
		
		BDDMockito.when(emprestimoRepositoryMock.save(ArgumentMatchers.any(Emprestimo.class))).thenReturn(EmprestimoCreator.createValidEmprestimo());
		
		BDDMockito.doNothing().when(emprestimoRepositoryMock).deleteAuthenticatedUserLoanById(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
		
		BDDMockito.when(livroRepositoryMock.findAuthenticatedUserBooksById(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(LivroCreator.createValidLivro()));
		
		BDDMockito.when(userAuthenticated.userAuthenticated()).thenReturn(UsuarioCreator.createUserUsuario());
		
		BDDMockito.when(usuarioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(UsuarioCreator.createUserUsuario()));
	}
	
	@Test
	@DisplayName("find all user books by id Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Emprestimo emprestimo = EmprestimoCreator.createValidEmprestimo();
		
		Page<Emprestimo> emprestimoPage = this.emprestimoService.findAll(PageRequest.of(0, 1));
		
		Assertions.assertThat(emprestimoPage).isNotNull().isNotEmpty();
		Assertions.assertThat(emprestimoPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(emprestimoPage.toList().get(0)).isEqualTo(emprestimo);
	}
	
	@Test
	@DisplayName("find all user books by id Return List Of Emprestimo whenSuccessful")
	void findAll_ReturnListOfEmprestimo_whenSuccessful() {
		Emprestimo emprestimoSaved = EmprestimoCreator.createValidEmprestimo();
		
		List<Emprestimo> emprestimo = this.emprestimoService.findAllNonPageable();
		
		Assertions.assertThat(emprestimo).isNotNull().isNotEmpty();
		Assertions.assertThat(emprestimo.get(0).getId()).isNotNull();
		Assertions.assertThat(emprestimo.get(0)).isEqualTo(emprestimoSaved);
	}
	
	@Test
	@DisplayName("findById return emprestimo whenSuccessful")
	void findById_ReturnEmprestimo_whenSuccessful() {
		Emprestimo emprestimoSaved = EmprestimoCreator.createValidEmprestimo();
		
		Emprestimo emprestimo = this.emprestimoService.findByIdOrElseThrowResourceNotFoundException(1);
		
		Assertions.assertThat(emprestimo).isNotNull();
		Assertions.assertThat(emprestimo.getId()).isNotNull();
		Assertions.assertThat(emprestimo).isEqualTo(emprestimoSaved);
	}
	
	@Test
	@DisplayName("findById Return Bad Request Exception When Emprestimo Is Not Found")
	void findById_ReturnBadRequestExceptionWhenEmprestimoIsNotFound() {
		BDDMockito.when(emprestimoRepositoryMock.findAuthenticatedUserById(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatCode(() -> this.emprestimoService.findByIdOrElseThrowResourceNotFoundException(1))
		.isInstanceOf(BadRequestException.class);
	}
	
	
	
	@Test
	@DisplayName("save Return Emprestimo whenSuccessful")
	void save_ReturnEmprestimo_whenSuccessful() {
		Emprestimo emprestimoSaved = EmprestimoCreator.createValidEmprestimo();
		
		Emprestimo emprestimo = this.emprestimoService.save(EmprestimoPostRequestBodyCreator.createEmprestimoPostRequestBodyCreator(),1l);
		
		Assertions.assertThat(emprestimo).isNotNull();
		Assertions.assertThat(emprestimo.getId()).isNotNull();
		Assertions.assertThat(emprestimo).isEqualTo(emprestimoSaved);
	}
	
	@Test
	@DisplayName("delete Removes Emprestimo whenSuccessful")
	void delete_RemovesEmprestimo_whenSuccessful() {	
		Assertions.assertThatCode(() -> this.emprestimoService.delete(1l)).doesNotThrowAnyException();
	}
	
	@Test
	@DisplayName("update Replace Emprestimo whenSuccessful")
	void update_ReplaveEmprestimo_whenSuccessful() {			
		Assertions.assertThatCode(() -> this.emprestimoService.update(EmprestimoPutRequestBodyCreator.createEmprestimoPutRequestBodyCreator())).doesNotThrowAnyException();
	}
	
}
