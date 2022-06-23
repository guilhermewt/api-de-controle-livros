package com.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Autor;
import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioAutor;
import com.biblioteca.repository.RepositorioEditora;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;

@Service
public class serviceLivro {
	
	@Autowired
	private RepositorioLivro repositorio;
	
	@Autowired
	private RepositorioEditora editoraRepositorio;
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private RepositorioAutor autorRepositorio;
	
	public List<Livro> findAll(){
		return repositorio.findAll();
	}
	
	public Livro findById(long id) {
		return repositorio.findById(id).get();
	}
	
	public Livro insert(Livro obj,long idUsuario,long idEditora,long idAutor) {
		Usuario user = repositorioUsuario.findById(idUsuario).get();
		Editora editora = editoraRepositorio.findById(idEditora).get();
		Autor autor = autorRepositorio.findById(idAutor).get();
		obj.setUsuario(user);
		obj.setEditora(editora);
		obj.setAutor(autor);
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
