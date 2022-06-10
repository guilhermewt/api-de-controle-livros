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

import com.biblioteca.entities.Livro;
import com.biblioteca.services.serviceLivro;

@RestController
@RequestMapping(value = "/livros")
public class LivroResources {

	@Autowired
	private serviceLivro serviceLivro;
	
	@GetMapping
	public ResponseEntity<List<Livro>> findAll(){
		List<Livro> list = serviceLivro.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}")
	public ResponseEntity<Livro> findById(@PathVariable long id){
		Livro usuario = serviceLivro.findById(id);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Livro> insert(@RequestBody Livro obj){
		Livro usuario = serviceLivro.insert(obj);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Livro> delete(@PathVariable long id){
		serviceLivro.delete(id);
		return ResponseEntity.noContent().build();
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Livro> findById(@RequestBody Livro obj,  @PathVariable long id){
		obj.setId(id);
		Livro usuario = serviceLivro.update(obj);
		return ResponseEntity.ok().body(usuario);
	}
}
