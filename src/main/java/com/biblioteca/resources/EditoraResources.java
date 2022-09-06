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

import com.biblioteca.entities.Editora;
import com.biblioteca.requests.EditoraPostRequestBody;
import com.biblioteca.requests.EditoraPutRequestBody;
import com.biblioteca.services.serviceEditora;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/editoras")
@RequiredArgsConstructor
public class EditoraResources {

	private final serviceEditora serviceEditora;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Editora>> findAllNonPageable(){
		return ResponseEntity.ok(serviceEditora.findAllNonPageable());
	}
	
	@GetMapping
	public ResponseEntity<Page<Editora>> findAll(Pageable pageable){
		return ResponseEntity.ok(serviceEditora.findAll(pageable));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Editora> findById(@PathVariable long id){
		return ResponseEntity.ok(serviceEditora.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	@PostMapping
	public ResponseEntity<Editora> save(@RequestBody @Valid EditoraPostRequestBody editoraPostRequestBody){
		return new ResponseEntity<>(serviceEditora.save(editoraPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Editora> delete(@PathVariable long id){
		serviceEditora.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
    @PutMapping()
	public ResponseEntity<Editora> update(@RequestBody EditoraPutRequestBody editoraPutRequestBody){
		serviceEditora.update(editoraPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
