package com.biblioteca.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.Usuario;
import com.biblioteca.services.serviceUsuario;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResources {

	@Autowired
	private serviceUsuario serviceUsuario;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAll(){
		List<Usuario> list = serviceUsuario.findAll();
		return ResponseEntity.ok().body(list);
	}
}
