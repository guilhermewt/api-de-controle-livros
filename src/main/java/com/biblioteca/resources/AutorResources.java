package com.biblioteca.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.biblioteca.entities.Autor;
import com.biblioteca.requests.AutorPostRequestBody;
import com.biblioteca.requests.AutorPutRequestBody;
import com.biblioteca.services.serviceAutor;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/autores")
@RequiredArgsConstructor
public class AutorResources {

	private final serviceAutor serviceAutor;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Autor>> findAllNonPageable(){
		return ResponseEntity.ok(serviceAutor.findAllNonPageable());
	}
	
	@GetMapping
	public ResponseEntity<Page<Autor>> findAll(Pageable pageable){
		return ResponseEntity.ok(serviceAutor.findAll(pageable));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Autor> findById(@PathVariable long id){
		return ResponseEntity.ok(serviceAutor.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	@PostMapping
	public ResponseEntity<Autor> save(@RequestBody @Valid AutorPostRequestBody autorPostRequestBody){
		return new ResponseEntity<>(serviceAutor.save(autorPostRequestBody),HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Autor> delete(@PathVariable long id){
		serviceAutor.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
	@PutMapping
	public ResponseEntity<Autor> update(@RequestBody AutorPutRequestBody autorPutRequestBody){
		serviceAutor.update(autorPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
