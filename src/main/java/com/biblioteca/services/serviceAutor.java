package com.biblioteca.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Autor;
import com.biblioteca.repository.RepositorioAutor;
import com.biblioteca.requests.AutorPostRequestBody;
import com.biblioteca.requests.AutorPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceAutor {

	private final RepositorioAutor serviceRepositorio;

	public List<Autor> findAll() {
		return serviceRepositorio.findAll();
	}

	public Autor findByIdOrElseThrowResourceNotFoundException(long id) {
		return serviceRepositorio.findById(id).orElseThrow(() -> new BadRequestException("autor not found"));
	}

	public Autor insert(AutorPostRequestBody autorPostRequestBody) {
		Autor autor = Autor.builder().nome(autorPostRequestBody.getNome()).build();
		return serviceRepositorio.save(autor);
	}

	public void delete(long id) {
		try {
			serviceRepositorio.delete(findByIdOrElseThrowResourceNotFoundException(id));
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public void update(AutorPutRequestBody autorPutRequestBody) {
		Autor savedUsuario = serviceRepositorio.findById(autorPutRequestBody.getId()).get();
		serviceRepositorio.save(Autor.builder().id(savedUsuario.getId()).nome(autorPutRequestBody.getNome()).build());
	}

}
