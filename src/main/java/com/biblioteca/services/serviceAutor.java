package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Autor;
import com.biblioteca.repository.RepositorioAutor;
import com.biblioteca.services.exceptions.DatabaseException;
import com.biblioteca.services.exceptions.ResourceNotFoundException;

@Service
public class serviceAutor {

	@Autowired
	private RepositorioAutor serviceRepositorio;

	public List<Autor> findAll() {
		return serviceRepositorio.findAll();
	}

	public Autor findById(long id) {
		Optional<Autor> autor = serviceRepositorio.findById(id);
		return autor.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Autor insert(Autor obj) {
		return serviceRepositorio.save(obj);
	}

	public void delete(long id) {
		try {
			findById(id);
			serviceRepositorio.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Autor update(Autor obj) {
		Autor usuario = serviceRepositorio.findById(obj.getId()).get();
		updateData(usuario, obj);
		return serviceRepositorio.save(usuario);
	}

	private void updateData(Autor usuario, Autor obj) {
		usuario.setNome(obj.getNome());
	}
}
