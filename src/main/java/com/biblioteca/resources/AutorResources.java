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

import com.biblioteca.entities.Autor;
import com.biblioteca.requests.AutorPostRequestBody;
import com.biblioteca.requests.AutorPutRequestBody;
import com.biblioteca.services.serviceAutor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/autores")
@RequiredArgsConstructor
public class AutorResources {

	private final serviceAutor serviceAutor;
	
	@GetMapping(value = "/all")
	@Operation(summary = "find all author non paginated")
	public ResponseEntity<List<Autor>> findAllNonPageable(){
		return ResponseEntity.ok(serviceAutor.findAllNonPageable());
	}
	
	@GetMapping
	@Operation(summary = "find all author paginated", description = "the default size is 20, use the parameter to change de default value")
	public ResponseEntity<Page<Autor>> findAll(@ParameterObject Pageable pageable){
		return ResponseEntity.ok(serviceAutor.findAll(pageable));
	}
	
	@GetMapping(value = "/find")
	@Operation(summary = "find all author by name")
	public ResponseEntity<List<Autor>> findByName(@RequestParam String name){
		return ResponseEntity.ok(serviceAutor.findByName(name));
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "find author by Id")
	public ResponseEntity<Autor> findById(@PathVariable long id){
		return ResponseEntity.ok(serviceAutor.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	@PostMapping
	public ResponseEntity<Autor> save(@RequestBody @Valid AutorPostRequestBody autorPostRequestBody){
		return new ResponseEntity<>(serviceAutor.save(autorPostRequestBody),HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when author does not exist in the dataBase")
	})
	public ResponseEntity<Void> delete(@PathVariable long id){
		serviceAutor.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
	@PutMapping
	public ResponseEntity<Void> update(@RequestBody AutorPutRequestBody autorPutRequestBody){
		serviceAutor.update(autorPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
