package com.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Editora;
import com.biblioteca.repository.RepositorioEditora;

@Service
public class serviceEditora {
	
	@Autowired
	private RepositorioEditora service;
	
	
	public List<Editora> findAll(){
		return service.findAll();
	}
	
	public Editora findById(long id) {
		return service.findById(id).get();
	}
	
	public Editora insert(Editora obj) {
		return service.save(obj);
	}
	
	public void delete(long id) {
		findById(id);
		service.deleteById(id);
	}
	
	public Editora update(Editora obj) {
		Editora usuario = service.findById(obj.getId()).get();
		updateData(usuario,obj);
		return service.save(usuario);
	}

	private void updateData(Editora usuario, Editora obj) {
		usuario.setNome(obj.getNome());
	}
}
