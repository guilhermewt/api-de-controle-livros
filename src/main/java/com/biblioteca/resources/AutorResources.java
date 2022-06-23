package com.biblioteca.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.Autor;
import com.biblioteca.services.serviceAutor;

@RestController
@RequestMapping(value = "/autor")
public class AutorResources {

	@Autowired
	private serviceAutor serviceAutor;
	
	@GetMapping
	public ResponseEntity<List<Autor>> findAll(){
		List<Autor> list = serviceAutor.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}")
	public ResponseEntity<Autor> findById(@PathVariable long id){
		Autor usuario = serviceAutor.findById(id);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Autor> insert(@RequestBody Autor obj){
		Autor usuario = serviceAutor.insert(obj);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Autor> delete(@PathVariable long id){
		serviceAutor.delete(id);
		return ResponseEntity.noContent().build();
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Autor> findById(@RequestBody Autor obj,  @PathVariable long id){
		obj.setId(id);
		Autor usuario = serviceAutor.update(obj);
		return ResponseEntity.ok().body(usuario);
	}
}
