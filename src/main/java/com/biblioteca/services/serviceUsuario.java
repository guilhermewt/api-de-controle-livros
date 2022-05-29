package com.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioUsuario;

@Service
public class serviceUsuario {
	
	@Autowired
	private RepositorioUsuario service;
	
	public List<Usuario> findAll(){
		return service.findAll();
	}
}
