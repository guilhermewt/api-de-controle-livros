package com.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;

@Service
public class serviceLivro {
	
	@Autowired
	private RepositorioLivro repositorio;
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	public List<Livro> findAll(){
		return repositorio.findAll();
	}
	
	public Livro findById(long id) {
		return repositorio.findById(id).get();
	}
	
	public Livro insert(Livro obj,long idUsuario) {
		Usuario user = repositorioUsuario.findById(idUsuario).get();
		obj.setUsuario(user);
		return repositorio.save(obj);
	}
	
	public void delete(long id) {
		findById(id);
		repositorio.deleteById(id);
	}
	
	public Livro update(Livro obj) {
		Livro Livro = repositorio.findById(obj.getId()).get();
		updateData(Livro,obj);
		return repositorio.save(Livro);
	}

	private void updateData(Livro Livro, Livro obj) {
		Livro.setTitulo(obj.getTitulo());
		Livro.setAnoPublicacao(obj.getAnoPublicacao());
		
	}
}
