package com.biblioteca.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.Usuario;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResources {

	@GetMapping
	public ResponseEntity<Usuario> findAll(){
		Usuario user = new Usuario(1l,"guilherme", "guilherme@gmail","gui","1234567");
		return ResponseEntity.ok().body(user);
	}
}
