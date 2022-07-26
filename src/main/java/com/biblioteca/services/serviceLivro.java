package com.biblioteca.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Autor;
import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioAutor;
import com.biblioteca.repository.RepositorioEditora;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceLivro {

	private final RepositorioLivro livroRepositorio;

	private final RepositorioEditora editoraRepositorio;

	private final RepositorioUsuario UsuarioRepositorio;

	private final RepositorioAutor autorRepositorio;

	public List<Livro> findAll() {
		return livroRepositorio.findAll();
	}

	public Livro findByIdOrElseThrowResourceNotFoundException(long id) {
		return  livroRepositorio.findById(id).orElseThrow(() -> new BadRequestException("livro not founnd"));
	}

	public Livro insert(Livro obj, long idUsuario, long idEditora, long idAutor) {
		Usuario user = UsuarioRepositorio.findById(idUsuario).get();
		Editora editora = editoraRepositorio.findById(idEditora).get();
		Autor autor = autorRepositorio.findById(idAutor).get();
		obj.setUsuario(user);
		obj.setEditora(editora);
		obj.setAutor(autor);
		return livroRepositorio.save(obj);
	}

	public void delete(long id) {
		try {
			livroRepositorio.delete(findByIdOrElseThrowResourceNotFoundException(id));
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public Livro update(Livro obj) {
		Livro Livro = livroRepositorio.findById(obj.getId()).get();
		updateData(Livro, obj);
		return livroRepositorio.save(Livro);
	}

	private void updateData(Livro Livro, Livro obj) {
		Livro.setTitulo(obj.getTitulo());
		Livro.setAnoPublicacao(obj.getAnoPublicacao());

	}
}
