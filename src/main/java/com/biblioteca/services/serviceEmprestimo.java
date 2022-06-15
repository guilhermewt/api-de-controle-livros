package com.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioEmprestimo;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;

@Service
public class serviceEmprestimo {
	
	@Autowired
	private RepositorioEmprestimo repositorioEmprestimo;
	
	@Autowired
	private RepositorioUsuario userRepositorio;
	
	@Autowired
	private RepositorioLivro livroRepositorio;
	
	public List<Emprestimo> findAll(){
		return repositorioEmprestimo.findAll();
	}
	
	public Emprestimo findById(long id) {
		return repositorioEmprestimo.findById(id).get();
	}
	
	public Emprestimo insert(long id,Emprestimo obj,long idLivro) {
		Livro livro = livroRepositorio.findById(idLivro).get();
		Usuario usuario = userRepositorio.findById(id).get();
		
		boolean temLivro = usuario.getLivro().contains(livro);
		if(temLivro == true) {
		  obj.setUsuario(usuario);
		  obj.getLivros().add(livro);
		  return repositorioEmprestimo.save(obj);
		}
		else {
			throw new IllegalAccessError("este livro nao pertence ao usuario");
		}
		
	}
	
	public void delete(long id) {
		findById(id);
		repositorioEmprestimo.deleteById(id);
	}
	
	public Emprestimo update(Emprestimo obj) {
		Emprestimo Emprestimo = repositorioEmprestimo.findById(obj.getId()).get();
		updateData(Emprestimo,obj);
		return repositorioEmprestimo.save(Emprestimo);
	}

	private void updateData(Emprestimo Emprestimo, Emprestimo obj) {
		Emprestimo.setDataEmprestimo(obj.getDataEmprestimo());
		Emprestimo.setDataDevolucao(obj.getDataDevolucao());
		
	}
}
