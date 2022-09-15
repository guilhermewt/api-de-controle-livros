package com.biblioteca.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Editora;
import com.biblioteca.mapper.EditoraMapper;
import com.biblioteca.repository.RepositorioEditora;
import com.biblioteca.requests.EditoraPostRequestBody;
import com.biblioteca.requests.EditoraPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceEditora {
	
	private final RepositorioEditora editoraRepositorio;

	public List<Editora> findAllNonPageable() {
		return editoraRepositorio.findAll();
	}
	
	public Page<Editora> findAll(Pageable pageable) {
		return editoraRepositorio.findAll(pageable);
	}
	
	public List<Editora> findByName(String name) {
		return editoraRepositorio.findByNomeContainingIgnoreCase(name);
	}

	public Editora findByIdOrElseThrowResourceNotFoundException(long id) {
		return editoraRepositorio.findById(id).orElseThrow(() -> new BadRequestException("editora not found"));
	}

	@Transactional
	public Editora save(EditoraPostRequestBody editoraPostRequestBody) {
		return editoraRepositorio.save(EditoraMapper.INSTANCE.toEditora(editoraPostRequestBody));
	}

	public void delete(long id) {
		try {
			editoraRepositorio.delete(findByIdOrElseThrowResourceNotFoundException(id));
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public void update(EditoraPutRequestBody editoraPutRequestBody) {
		Editora editoraSaved = editoraRepositorio.findById(editoraPutRequestBody.getId()).get();
		Editora editora = EditoraMapper.INSTANCE.toEditora(editoraPutRequestBody);
		editora.setId(editoraSaved.getId());
		
		editoraRepositorio.save(editora);
	}

}
