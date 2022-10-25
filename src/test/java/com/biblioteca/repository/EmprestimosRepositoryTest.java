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
import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.util.EmprestimoCreator;
import com.biblioteca.util.LivroCreator;
import com.biblioteca.util.UsuarioCreator;

@DataJpaTest
@DisplayName("test for emprestimo repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmprestimosRepositoryTest {
	
	@Autowired
	private RepositorioEmprestimo repositoryEmprestimo;
	
	@Autowired
	private RepositorioUsuario userRepository;
	
	@Autowired
	private RepositorioLivro livroRepository;
	
	@Test
	@DisplayName("find all user books by id return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Usuario usuario = this.userRepository.save(UsuarioCreator.createUserUsuario());
		
		Emprestimo emprestimoToBeSaved = this.repositoryEmprestimo.save(EmprestimoCreator.createValidEmprestimo());
		Page<Emprestimo> emprestimoSaved = this.repositoryEmprestimo.findByUsuarioId(usuario.getId(), PageRequest.of(0, 5));
		
		Assertions.assertThat(emprestimoSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(emprestimoSaved.toList().get(0)).isEqualTo(emprestimoToBeSaved);
	}
	
	@Test
	@DisplayName("find all user books by id return list of emprestimo whensuccessful")
	void findAll_returnListOfEmprestimo_whenSuccessful() {
		this.userRepository.save(UsuarioCreator.createUserUsuario());
		
		Emprestimo emprestimoToBeSaved = this.repositoryEmprestimo.save(EmprestimoCreator.createValidEmprestimo());
		List<Emprestimo> emprestimoSaved = this.repositoryEmprestimo.findAll();
		
		Assertions.assertThat(emprestimoSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(emprestimoSaved.get(0)).isEqualTo(emprestimoToBeSaved);
	}
	
	@Test
	@DisplayName("findById return emprestimo whenSuccessful")
	void findByid_returnEmprestimo_whenSuccessful() {
		Usuario usuario = this.userRepository.save(UsuarioCreator.createUserUsuario());
		
		Emprestimo emprestimoToBeSaved = this.repositoryEmprestimo.save(EmprestimoCreator.createValidEmprestimo());
		Emprestimo emprestimoSaved = this.repositoryEmprestimo.findAuthenticatedUserById(emprestimoToBeSaved.getId(),usuario.getId()).get();
		
		Assertions.assertThat(emprestimoSaved).isNotNull();
		Assertions.assertThat(emprestimoSaved.getId()).isNotNull();
		Assertions.assertThat(emprestimoSaved).isEqualTo(emprestimoToBeSaved);
	}
	
	
	
	@Test
	@DisplayName("save return emprestimo whenSuccessful")
	void save_returnEmprestimo_whenSuccessful() {
		this.userRepository.save(UsuarioCreator.createUserUsuario());
		Livro livro = this.livroRepository.save(LivroCreator.createValidLivro());
		
		Emprestimo emprestimoToBeSaved = EmprestimoCreator.createValidEmprestimo();
		emprestimoToBeSaved.getLivros().add(livro);
		
		Emprestimo emprestimoSaved = this.repositoryEmprestimo.save(emprestimoToBeSaved);
		
		Assertions.assertThat(emprestimoSaved).isNotNull();
		Assertions.assertThat(emprestimoSaved.getId()).isNotNull();
		Assertions.assertThat(emprestimoSaved).isEqualTo(emprestimoToBeSaved);
	}
	
	@Test
	@DisplayName("delete removes emprestimo whenSuccessful")
	void delete_removesEmprestimo_whenSuccessful() {
		Usuario usuario = this.userRepository.save(UsuarioCreator.createUserUsuario());
		Livro livro = this.livroRepository.save(LivroCreator.createValidLivro());
		
		Emprestimo emprestimoToBeSaved = EmprestimoCreator.createValidEmprestimo();
		emprestimoToBeSaved.getLivros().add(livro);
		
		Emprestimo emprestimoSaved = this.repositoryEmprestimo.save(emprestimoToBeSaved);
		
	    this.repositoryEmprestimo.deleteAuthenticatedUserLoanById(livro.getId(), usuario.getId());;
	    
	    Optional<Emprestimo> emprestimoDeleted = this.repositoryEmprestimo.findAuthenticatedUserById(emprestimoSaved.getId(),usuario.getId());
	    
	    Assertions.assertThat(emprestimoDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace emprestimo whenSuccessful")
	void update_replaceEmprestimo_whenSuccessful() {
		this.userRepository.save(UsuarioCreator.createUserUsuario());
		
		this.repositoryEmprestimo.save(EmprestimoCreator.createValidEmprestimo());
		
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
