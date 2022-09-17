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

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.util.EmprestimoCreator;

@DataJpaTest
@DisplayName("test for emprestimo repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmprestimosRepositoryTest {
	
	@Autowired
	private RepositorioEmprestimo repositoryEmprestimo;
	
	@Test
	@DisplayName("find all return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		
		Emprestimo emprestimoToBeSaved = this.repositoryEmprestimo.save(EmprestimoCreator.createEmprestimoToBeSaved());
		Page<Emprestimo> emprestimoSaved = this.repositoryEmprestimo.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(emprestimoSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(emprestimoSaved.toList().get(0)).isEqualTo(emprestimoToBeSaved);
	}
	
	@Test
	@DisplayName("find all return list of emprestimo whensuccessful")
	void findAll_returnListOfEmprestimo_whenSuccessful() {
		
		Emprestimo emprestimoToBeSaved = this.repositoryEmprestimo.save(EmprestimoCreator.createEmprestimoToBeSaved());
		List<Emprestimo> emprestimoSaved = this.repositoryEmprestimo.findAll();
		
		Assertions.assertThat(emprestimoSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(emprestimoSaved.get(0)).isEqualTo(emprestimoToBeSaved);
	}
	
	@Test
	@DisplayName("findById return emprestimo whenSuccessful")
	void findByid_returnEmprestimo_whenSuccessful() {
		Emprestimo emprestimoToBeSaved = this.repositoryEmprestimo.save(EmprestimoCreator.createValidEmprestimo());
		Emprestimo emprestimoSaved = this.repositoryEmprestimo.findById(emprestimoToBeSaved.getId()).get();
		
		Assertions.assertThat(emprestimoSaved).isNotNull();
		Assertions.assertThat(emprestimoSaved.getId()).isNotNull();
		Assertions.assertThat(emprestimoSaved).isEqualTo(emprestimoToBeSaved);
	}
	
	
	
	@Test
	@DisplayName("save return emprestimo whenSuccessful")
	void save_returnEmprestimo_whenSuccessful() {
		Emprestimo emprestimoToBeSaved = EmprestimoCreator.createEmprestimoToBeSaved();
		Emprestimo emprestimoSaved = this.repositoryEmprestimo.save(emprestimoToBeSaved);
		
		Assertions.assertThat(emprestimoSaved).isNotNull();
		Assertions.assertThat(emprestimoSaved.getId()).isNotNull();
		Assertions.assertThat(emprestimoSaved).isEqualTo(emprestimoSaved);
	}
	
	@Test
	@DisplayName("delete removes emprestimo whenSuccessful")
	void delete_removesEmprestimo_whenSuccessful() {
		Emprestimo emprestimoSaved = this.repositoryEmprestimo.save(EmprestimoCreator.createEmprestimoToBeSaved());
		
	    this.repositoryEmprestimo.deleteById(emprestimoSaved.getId());
	    
	    Optional<Emprestimo> emprestimoDeleted = this.repositoryEmprestimo.findById(emprestimoSaved.getId());
	    
	    Assertions.assertThat(emprestimoDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace emprestimo whenSuccessful")
	void update_replaceEmprestimo_whenSuccessful() {
		this.repositoryEmprestimo.save(EmprestimoCreator.createEmprestimoToBeSaved());
		
		Emprestimo emprestimoToBeUpdate = EmprestimoCreator.createUpdatedEmprestimo();
		
	    Emprestimo emprestimoUpdate = this.repositoryEmprestimo.save(emprestimoToBeUpdate);
	    
	    Assertions.assertThat(emprestimoUpdate).isNotNull();
	    Assertions.assertThat(emprestimoUpdate.getId()).isNotNull();
	    Assertions.assertThat(emprestimoUpdate).isEqualTo(emprestimoToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when emprestimo name is empty")
	void save_throwConstrationViolationException_whenEmprestimoNameIsEmpty() {
		Emprestimo emprestimo = new Emprestimo();
		
		Assertions.assertThatThrownBy(() -> this.repositoryEmprestimo.save(emprestimo))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
