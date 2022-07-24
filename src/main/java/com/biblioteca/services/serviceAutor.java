package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Autor;
import com.biblioteca.repository.RepositorioAutor;
import com.biblioteca.requests.AutorPostRequestBody;
import com.biblioteca.requests.AutorPutRequestBody;
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

	public Autor insert(AutorPostRequestBody autorPostRequestBody) {
		Autor autor = Autor.builder().nome(autorPostRequestBody.getNome()).build();
		return serviceRepositorio.save(autor);
	}

	public void delete(long id) {
		try {
			serviceRepositorio.delete(findById(id));
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public void update(AutorPutRequestBody autorPutRequestBody) {
		Autor savedUsuario = serviceRepositorio.findById(autorPutRequestBody.getId()).get();

		serviceRepositorio.save(Autor.builder().id(savedUsuario.getId()).nome(autorPutRequestBody.getNome()).build());
	}

}
