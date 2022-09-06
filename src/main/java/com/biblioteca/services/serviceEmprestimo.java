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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceEmprestimo {
	
	private final RepositorioEmprestimo emprestimoRepositorio;

	private final RepositorioUsuario userRepositorio;

	private final RepositorioLivro livroRepositorio;

	public List<Emprestimo> findAllNonPageable() {
		return emprestimoRepositorio.findAll();
	}
	
	public Page<Emprestimo> findAll(Pageable pageable) {
		return emprestimoRepositorio.findAll(pageable);
	}

	public Emprestimo findByIdOrElseThrowResourceNotFoundException(long id) {
		return emprestimoRepositorio.findById(id).orElseThrow(() -> new BadRequestException("emprestimo not found"));	
	}

	@Transactional
	public Emprestimo save(long id, EmprestimosPostRequestBody emprestimosPostRequestBody, long idLivro) {
		Livro livroSaved = livroRepositorio.findById(idLivro).get();
		Usuario usuarioSaved = userRepositorio.findById(id).get();
        Emprestimo emprestimo = EmprestimoMapper.INSTANCE.toEmprestimo(emprestimosPostRequestBody);
		
		boolean temLivro = usuarioSaved.getLivro().contains(livroSaved);
		if (temLivro == true) {
			emprestimo.setUsuario(usuarioSaved);
			emprestimo.getLivros().add(livroSaved);
			return emprestimoRepositorio.save(emprestimo);
		} else {
			throw new IllegalAccessError("este livro nao pertence ao usuario");
		}

	}

	public void delete(long id) {
		try {
			emprestimoRepositorio.delete(findByIdOrElseThrowResourceNotFoundException(id));
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public void update(EmprestimosPutRequestBody emprestimosPutRequestBody) {
		Emprestimo emprestimoSaved = emprestimoRepositorio.findById(emprestimosPutRequestBody.getId()).get();
		Emprestimo emprestimo = EmprestimoMapper.INSTANCE.toEmprestimmo(emprestimosPutRequestBody);
		emprestimo.setId(emprestimoSaved.getId());		
		emprestimoRepositorio.save(emprestimo);
	}
	
}
