package com.biblioteca.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioEmprestimo;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceEmprestimo {
	
	private final RepositorioEmprestimo emprestimoRepositorio;

	private final RepositorioUsuario userRepositorio;

	private final RepositorioLivro livroRepositorio;

	public List<Emprestimo> findAll() {
		return emprestimoRepositorio.findAll();
	}

	public Emprestimo findByIdOrElseThrowResourceNotFoundException(long id) {
		return emprestimoRepositorio.findById(id).orElseThrow(() -> new BadRequestException("emprestimo not found"));	
	}

	public Emprestimo insert(long id, Emprestimo obj, long idLivro) {
		Livro livro = livroRepositorio.findById(idLivro).get();
		Usuario usuario = userRepositorio.findById(id).get();

		boolean temLivro = usuario.getLivro().contains(livro);
		if (temLivro == true) {
			obj.setUsuario(usuario);
			obj.getLivros().add(livro);
			return emprestimoRepositorio.save(obj);
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

	public Emprestimo update(Emprestimo obj) {
		Emprestimo Emprestimo = emprestimoRepositorio.findById(obj.getId()).get();
		updateData(Emprestimo, obj);
		return emprestimoRepositorio.save(Emprestimo);
	}

	private void updateData(Emprestimo Emprestimo, Emprestimo obj) {
		Emprestimo.setDataEmprestimo(obj.getDataEmprestimo());
		Emprestimo.setDataDevolucao(obj.getDataDevolucao());

	}
}
