package com.biblioteca.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.Usuario;
import com.biblioteca.services.serviceUsuario;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResources {

	@Autowired
	private serviceUsuario serviceUsuario;
	
	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Usuario>> findAll(){
		List<Usuario> list = serviceUsuario.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Usuario> findById(@PathVariable long id){
		Usuario usuario = serviceUsuario.findById(id);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Usuario> insert(@RequestBody Usuario obj){
		Usuario usuario = serviceUsuario.insert(obj);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Usuario> delete(@PathVariable long id){
		serviceUsuario.delete(id);
		return ResponseEntity.noContent().build();
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Usuario> findById(@RequestBody Usuario obj,  @PathVariable long id){
		obj.setId(id);
		Usuario usuario = serviceUsuario.update(obj);
		return ResponseEntity.ok().body(usuario);
	}
}
