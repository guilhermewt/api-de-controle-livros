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

import com.biblioteca.entities.Editora;
import com.biblioteca.services.serviceEditora;

@RestController
@RequestMapping(value = "/editoras")
public class EditoraResources {

	@Autowired
	private serviceEditora serviceEditora;
	
	@GetMapping
	public ResponseEntity<List<Editora>> findAll(){
		List<Editora> list = serviceEditora.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}")
	public ResponseEntity<Editora> findById(@PathVariable long id){
		Editora usuario = serviceEditora.findById(id);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Editora> insert(@RequestBody Editora obj){
		Editora usuario = serviceEditora.insert(obj);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Editora> delete(@PathVariable long id){
		serviceEditora.delete(id);
		return ResponseEntity.noContent().build();
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Editora> findById(@RequestBody Editora obj,  @PathVariable long id){
		obj.setId(id);
		Editora usuario = serviceEditora.update(obj);
		return ResponseEntity.ok().body(usuario);
	}
}
