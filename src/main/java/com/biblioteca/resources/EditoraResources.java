package com.biblioteca.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.Editora;
import com.biblioteca.services.serviceEditora;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/editoras")
@RequiredArgsConstructor
public class EditoraResources {

	private final serviceEditora serviceEditora;
	
	@GetMapping
	public ResponseEntity<List<Editora>> findAll(){
		return ResponseEntity.ok(serviceEditora.findAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Editora> findById(@PathVariable long id){
		return ResponseEntity.ok(serviceEditora.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	@PostMapping
	public ResponseEntity<Editora> insert(@RequestBody Editora obj){
		return new ResponseEntity<Editora>(serviceEditora.insert(obj), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Editora> delete(@PathVariable long id){
		serviceEditora.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
    @PutMapping(value = "/{id}")
	public ResponseEntity<Editora> update(@RequestBody Editora obj,  @PathVariable long id){
		obj.setId(id);
		serviceEditora.update(obj);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
