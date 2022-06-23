package com.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Autor;
import com.biblioteca.repository.RepositorioAutor;

@Service
public class serviceAutor {
	
	@Autowired
	private RepositorioAutor service;
	
	
	public List<Autor> findAll(){
		return service.findAll();
	}
	
	public Autor findById(long id) {
		return service.findById(id).get();
	}
	
	public Autor insert(Autor obj) {
		return service.save(obj);
	}
	
	public void delete(long id) {
		findById(id);
		service.deleteById(id);
	}
	
	public Autor update(Autor obj) {
		Autor usuario = service.findById(obj.getId()).get();
		updateData(usuario,obj);
		return service.save(usuario);
	}

	private void updateData(Autor usuario, Autor obj) {
		usuario.setNome(obj.getNome());
	}
}
