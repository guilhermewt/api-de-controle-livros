package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Editora;
import com.biblioteca.repository.RepositorioEditora;
import com.biblioteca.services.exceptions.ResourceNotFoundException;

@Service
public class serviceEditora {
	
	@Autowired
	private RepositorioEditora editoraRepositorio;
	
	
	public List<Editora> findAll(){
		return editoraRepositorio.findAll();
	}
	
	public Editora findById(long id) {
		Optional<Editora> editora = editoraRepositorio.findById(id);
		return editora.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Editora insert(Editora obj) {
		return editoraRepositorio.save(obj);
	}
	
	public void delete(long id) {
		findById(id);
		editoraRepositorio.deleteById(id);
	}
	
	public Editora update(Editora obj) {
		Editora usuario = editoraRepositorio.findById(obj.getId()).get();
		updateData(usuario,obj);
		return editoraRepositorio.save(usuario);
	}

	private void updateData(Editora usuario, Editora obj) {
		usuario.setNome(obj.getNome());
	}
}
