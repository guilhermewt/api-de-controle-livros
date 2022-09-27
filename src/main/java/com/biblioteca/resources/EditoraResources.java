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

import com.biblioteca.entities.Editora;
import com.biblioteca.requests.EditoraPostRequestBody;
import com.biblioteca.requests.EditoraPutRequestBody;
import com.biblioteca.services.serviceEditora;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/editoras")
@RequiredArgsConstructor
public class EditoraResources {

	private final serviceEditora serviceEditora;
	
	@GetMapping(value = "/all")
	@Operation(summary = "find all editora that are not paginated")
	public ResponseEntity<List<Editora>> findAllNonPageable(){
		return ResponseEntity.ok(serviceEditora.findAllNonPageable());
	}
	
	@GetMapping
	@Operation(summary = "find all editora paginated", description = "the default size is 20, use the parameter to change the default value")
	public ResponseEntity<Page<Editora>> findAll(@ParameterObject Pageable pageable){
		return ResponseEntity.ok(serviceEditora.findAll(pageable));
	}
	
	@GetMapping(value = "/findByName")
	@Operation(summary = "find all editora by name")
	public ResponseEntity<List<Editora>> findByName(@RequestParam String name){
		return ResponseEntity.ok(serviceEditora.findByName(name));
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "find publisher by Id")
	public ResponseEntity<Editora> findById(@PathVariable long id){
		return ResponseEntity.ok(serviceEditora.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	@PostMapping
	public ResponseEntity<Editora> save(@RequestBody @Valid EditoraPostRequestBody editoraPostRequestBody){
		return new ResponseEntity<>(serviceEditora.save(editoraPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when editora does not exist in the dataBase")
	})
	public ResponseEntity<Void> delete(@PathVariable long id){
		serviceEditora.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
    @PutMapping()
	public ResponseEntity<Void> update(@RequestBody EditoraPutRequestBody editoraPutRequestBody){
		serviceEditora.update(editoraPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
