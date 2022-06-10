package com.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioEmprestimo;
import com.biblioteca.repository.RepositorioUsuario;

@Service
public class serviceEmprestimo {
	
	@Autowired
	private RepositorioEmprestimo repositorio;
	
	@Autowired
	private RepositorioUsuario userRepositorio;
	
	public List<Emprestimo> findAll(){
		return repositorio.findAll();
	}
	
	public Emprestimo findById(long id) {
		return repositorio.findById(id).get();
	}
	
	public Emprestimo insert(Emprestimo obj,long id) {
		Usuario usuario = userRepositorio.findById(id).get();
		obj.setUsuario(usuario);
		return repositorio.save(obj);
	}
	
	public void delete(long id) {
		findById(id);
		repositorio.deleteById(id);
	}
	
	public Emprestimo update(Emprestimo obj) {
		Emprestimo Emprestimo = repositorio.findById(obj.getId()).get();
		updateData(Emprestimo,obj);
		return repositorio.save(Emprestimo);
	}

	private void updateData(Emprestimo Emprestimo, Emprestimo obj) {
		Emprestimo.setDataEmprestimo(obj.getDataEmprestimo());
		Emprestimo.setDataDevolucao(obj.getDataDevolucao());
		
	}
}
