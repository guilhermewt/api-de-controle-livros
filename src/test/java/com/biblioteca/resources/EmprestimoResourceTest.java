package com.biblioteca.resources;

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

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.requests.EmprestimosPostRequestBody;
import com.biblioteca.services.serviceEmprestimo;
import com.biblioteca.util.EmprestimoCreator;
import com.biblioteca.util.EmprestimoPostRequestBodyCreator;
import com.biblioteca.util.EmprestimoPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class EmprestimoResourceTest {
	
	@InjectMocks
	private EmprestimoResources emprestimoResource;
	
	@Mock
	private serviceEmprestimo emprestimoServiceMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Emprestimo> emprestimoPage = new PageImpl<>(List.of(EmprestimoCreator.createValidEmprestimo()));
		BDDMockito.when(emprestimoServiceMock.findAll(ArgumentMatchers.any())).thenReturn(emprestimoPage);	
		
		BDDMockito.when(emprestimoServiceMock.findAllNonPageable())
		.thenReturn(List.of(EmprestimoCreator.createValidEmprestimo()));
		
		BDDMockito.when(emprestimoServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
		.thenReturn(EmprestimoCreator.createValidEmprestimo());
		
		BDDMockito.when(emprestimoServiceMock.save(ArgumentMatchers.anyLong(), ArgumentMatchers.any(EmprestimosPostRequestBody.class), ArgumentMatchers.anyLong()))
		.thenReturn(EmprestimoCreator.createValidEmprestimo());
		
		BDDMockito.doNothing().when(emprestimoServiceMock).delete(ArgumentMatchers.anyLong());
		
	}
	
	@Test
	@DisplayName("findAll_Return List Of Object Inside Page whenSuccessful")
	void findAll_ReturnListOfObjectInsidePage_whenSuccessful() {
		Emprestimo emprestimoSaved = EmprestimoCreator.createValidEmprestimo();
		
		Page<Emprestimo> emprestimo = this.emprestimoResource.findAll(null).getBody();
		
		Assertions.assertThat(emprestimo).isNotNull().isNotEmpty();
		Assertions.assertThat(emprestimo.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(emprestimo.toList().get(0)).isEqualTo(emprestimoSaved);		
	}
	
	@Test
	@DisplayName("findAll_Return List of emprestimo whenSuccessful")
	void findAll_ReturnListOfEmprestimo_whenSuccessful() {
		Emprestimo emprestimoSaved = EmprestimoCreator.createValidEmprestimo();
		
		List<Emprestimo> emprestimo = this.emprestimoResource.findAllNonPageable().getBody();
		
		Assertions.assertThat(emprestimo).isNotNull().isNotEmpty();
		Assertions.assertThat(emprestimo.get(0).getId()).isNotNull();
		Assertions.assertThat(emprestimo.get(0)).isEqualTo(emprestimoSaved);		
	}
	
	@Test
	@DisplayName("findById Return emprestimo whenSuccessful")
	void findById_ReturnEmprestimo_whenSuccessful() {
		Emprestimo emprestimoSaved = EmprestimoCreator.createValidEmprestimo();
		
		Emprestimo emprestimo = this.emprestimoResource.findById(1).getBody();
		
		Assertions.assertThat(emprestimo).isNotNull();
		Assertions.assertThat(emprestimo.getId()).isNotNull();
		Assertions.assertThat(emprestimo).isEqualTo(emprestimoSaved);		
	}
	
	@Test
	@DisplayName("save Return emprestimo whenSuccessful")
	void save_ReturnEmprestimo_whenSuccessful() {
		Emprestimo emprestimoSaved = EmprestimoCreator.createValidEmprestimo();
		
		Emprestimo emprestimo = this.emprestimoResource.save(1l,EmprestimoPostRequestBodyCreator.createEmprestimoPostRequestBodyCreator(),1l)
				.getBody();
		
		Assertions.assertThat(emprestimo).isNotNull();
		Assertions.assertThat(emprestimo.getId()).isNotNull();
		Assertions.assertThat(emprestimo).isEqualTo(emprestimoSaved);		
	}
	
	@Test
	@DisplayName("delete removes emprestimo whenSuccessful")
	void delete_removesEmprestimo_whenSuccessful() {		
		ResponseEntity<Void> emprestimo = this.emprestimoResource.delete(1);
		
		Assertions.assertThat(emprestimo.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace emprestimo whenSuccessful")
	void update_replaceEmprestimo_whenSuccessful() {		
	    this.emprestimoResource.save(1l,EmprestimoPostRequestBodyCreator.createEmprestimoPostRequestBodyCreator(),1l).getBody();
		
		ResponseEntity<Void> emprestimo = this.emprestimoResource.update(1l,1l,EmprestimoPutRequestBodyCreator.createEmprestimoPutRequestBodyCreator());
		
		Assertions.assertThat(emprestimo.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);					
	}
	
}
