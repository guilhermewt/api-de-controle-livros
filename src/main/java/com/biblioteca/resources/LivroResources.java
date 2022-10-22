package com.biblioteca.resources;

import java.util.List;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.Livro;
import com.biblioteca.requests.LivroPostRequestBody;
import com.biblioteca.requests.LivroPutRequestBody;
import com.biblioteca.services.serviceLivro;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/livros")
@RequiredArgsConstructor
public class LivroResources {

	private final serviceLivro serviceLivro;


	@GetMapping(value = "/all")
	@Operation(summary = "find all books non paginated")
	public ResponseEntity<List<Livro>> findAllNonPageable(){
		return ResponseEntity.ok(serviceLivro.findAllNonPageable());
	}
	
	@GetMapping
	@Operation(summary = "find all books paginated", description = "the default size is 20, use the parameter to change the default value")
	public ResponseEntity<Page<Livro>> findAll(@ParameterObject Pageable pageable){
		return ResponseEntity.ok(serviceLivro.findAll(pageable));
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "find book by id")
	public ResponseEntity<Livro> findById(@PathVariable long id){
		return ResponseEntity.ok(serviceLivro.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	@GetMapping(value = "/findbytitulo")
	@Operation(summary = "find book by title")
	public ResponseEntity<List<Livro>> findByTitulo(@RequestParam String titulo){
		return ResponseEntity.ok(serviceLivro.findByTitulo(titulo));
	}
	
	//http://localhost:8080/livros/3/1
	@PostMapping()
	@Operation(description = "for the book to be made,the publisher Id and the author Id are required")
	public ResponseEntity<Livro> save(@RequestBody @Valid LivroPostRequestBody livroPostRequestBody){
		return new ResponseEntity<Livro>(serviceLivro.save(livroPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when publisher does not exist in the dataBase")
	})
	public ResponseEntity<Void> delete(@PathVariable long id){
		serviceLivro.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
    @PutMapping
    @Operation(description = "for the book to be made, the user Id,the publisher Id and the author Id are required")
	public ResponseEntity<Void> update(@RequestBody @Valid LivroPutRequestBody livroPutRequestBody){
		serviceLivro.update(livroPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
