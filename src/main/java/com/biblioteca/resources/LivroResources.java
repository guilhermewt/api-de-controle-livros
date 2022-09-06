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

import com.biblioteca.entities.Livro;
import com.biblioteca.requests.LivroPostRequestBody;
import com.biblioteca.requests.LivroPutRequestBody;
import com.biblioteca.services.serviceLivro;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/livros")
@RequiredArgsConstructor
public class LivroResources {

	private final serviceLivro serviceLivro;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Livro>> findAllNonPageable(){
		return ResponseEntity.ok(serviceLivro.findAllNonPageable());
	}
	
	@GetMapping
	public ResponseEntity<Page<Livro>> findAll(Pageable pageable){
		return ResponseEntity.ok(serviceLivro.findAll(pageable));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Livro> findById(@PathVariable long id){
		return ResponseEntity.ok(serviceLivro.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	//http://localhost:8080/livros/3/1
	@PostMapping(path = "/{idUsuario}/{idEditora}/{idAutor}")
	public ResponseEntity<Livro> save(@PathVariable long idUsuario,@PathVariable long idEditora,@PathVariable long idAutor, @RequestBody @Valid LivroPostRequestBody livroPostRequestBody){
		return new ResponseEntity<Livro>(serviceLivro.save(livroPostRequestBody,idUsuario,idEditora,idAutor), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Livro> delete(@PathVariable long id){
		serviceLivro.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
    @PutMapping
	public ResponseEntity<Livro> update(@RequestBody LivroPutRequestBody livroPutRequestBody){
		serviceLivro.update(livroPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
