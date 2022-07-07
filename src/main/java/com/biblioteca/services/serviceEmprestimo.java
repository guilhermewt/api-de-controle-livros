package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioEmprestimo;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.services.exceptions.DatabaseException;
import com.biblioteca.services.exceptions.ResourceNotFoundException;

@Service
public class serviceEmprestimo {

	@Autowired
	private RepositorioEmprestimo emprestimoRepositorio;

	@Autowired
	private RepositorioUsuario userRepositorio;

	@Autowired
	private RepositorioLivro livroRepositorio;

	public List<Emprestimo> findAll() {
		return emprestimoRepositorio.findAll();
	}

	public Emprestimo findById(long id) {
		Optional<Emprestimo> emprestimo = emprestimoRepositorio.findById(id);
		return emprestimo.orElseThrow(() -> new ResourceNotFoundException(id));
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
			findById(id);
			emprestimoRepositorio.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
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
