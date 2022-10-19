package com.biblioteca.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.mapper.EmprestimoMapper;
import com.biblioteca.repository.RepositorioEmprestimo;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.requests.EmprestimosPostRequestBody;
import com.biblioteca.requests.EmprestimosPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceEmprestimo {
	//testar todos metodos para fazer o commit
	
	private final RepositorioEmprestimo emprestimoRepositorio;

	private final RepositorioUsuario userRepositorio;

	private final RepositorioLivro livroRepositorio;
	
	private final GetUserDetails userAuthenticated;
	

	public List<Emprestimo> findAllNonPageable() {
		return emprestimoRepositorio.findByUsuarioId(userAuthenticated.userAuthenticated().getId());
	}
	
	public Page<Emprestimo> findAll(Pageable pageable) {
		return emprestimoRepositorio.findByUsuarioId(userAuthenticated.userAuthenticated().getId(), pageable);
	}

	public Emprestimo findByIdOrElseThrowResourceNotFoundException(long id) {
		return emprestimoRepositorio.findAuthenticatedUserById(id, userAuthenticated.userAuthenticated().getId()).orElseThrow(() -> new BadRequestException("emprestimo not found"));	
	}

	@Transactional
	public Emprestimo save(EmprestimosPostRequestBody emprestimosPostRequestBody, long idLivro) {
		Livro livroSaved = livroRepositorio.findAuthenticatedUserBooksById(idLivro, userAuthenticated.userAuthenticated().getId())
				.orElseThrow(() -> new BadRequestException("livro not found"));
		
		Usuario usuarioSaved = userRepositorio.findById(userAuthenticated.userAuthenticated().getId()).get();
        
		Emprestimo emprestimo = EmprestimoMapper.INSTANCE.toEmprestimo(emprestimosPostRequestBody);
		
		
		emprestimo.setUsuario(usuarioSaved);
		emprestimo.getLivros().add(livroSaved);
		return emprestimoRepositorio.save(emprestimo);
	}

	public void delete(long idBook) {
		try {
			emprestimoRepositorio.deleteAuthenticatedUserBookById(findByIdOrElseThrowResourceNotFoundException(idBook).getId(),userAuthenticated.userAuthenticated().getId());
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	
	public void update(EmprestimosPutRequestBody emprestimosPutRequestBody) {
		Emprestimo emprestimo = emprestimoRepositorio
				.findAuthenticatedUserById(emprestimosPutRequestBody.getId(),userAuthenticated.userAuthenticated().getId())
				.orElseThrow(() -> new BadRequestException("livro not found"));
		
		EmprestimoMapper.INSTANCE.atualizeEmprestimo(emprestimosPutRequestBody, emprestimo);
				
		emprestimoRepositorio.save(emprestimo);
	}
	
}
