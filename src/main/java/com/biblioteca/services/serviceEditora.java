package com.biblioteca.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Editora;
import com.biblioteca.repository.RepositorioEditora;
import com.biblioteca.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceEditora {
	
	private final RepositorioEditora editoraRepositorio;

	public List<Editora> findAll() {
		return editoraRepositorio.findAll();
	}

	public Editora findByIdOrElseThrowResourceNotFoundException(long id) {
		return editoraRepositorio.findById(id).orElseThrow(() -> new BadRequestException("editora not found"));
	}

	public Editora insert(Editora obj) {
		return editoraRepositorio.save(obj);
	}

	public void delete(long id) {
		try {
			editoraRepositorio.delete(findByIdOrElseThrowResourceNotFoundException(id));
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public Editora update(Editora obj) {
		Editora usuario = editoraRepositorio.findById(obj.getId()).get();
		updateData(usuario, obj);
		return editoraRepositorio.save(usuario);
	}

	private void updateData(Editora usuario, Editora obj) {
		usuario.setNome(obj.getNome());
	}
}
