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
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.Loan;
import com.biblioteca.requests.LoanPostRequestBody;
import com.biblioteca.requests.LoanPutRequestBody;
import com.biblioteca.services.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/loans")
@RequiredArgsConstructor
public class LoanResources {

	private final LoanService serviceLoan;
	
	@GetMapping(value = "/all")
	@Operation(summary = "find all loans that are not paginated")
	public ResponseEntity<List<Loan>> findAllNonPageable(){
		return ResponseEntity.ok(serviceLoan.findAllNonPageable());
	}
	
	@GetMapping
	@Operation(summary = "find all loans paginated", description = "the default size is 20, use the parameter to change the default value")
	public ResponseEntity<Page<Loan>> findAll(@ParameterObject Pageable pageable){
		return ResponseEntity.ok(serviceLoan.findAll(pageable));
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "find loan by Id")
	public ResponseEntity<Loan> findById(@PathVariable long id){
		return ResponseEntity.ok(serviceLoan.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	@PostMapping(path = "/{idBook}")
	@Operation(description = "for the loan to be made, the user id and the book id are required")
	public ResponseEntity<Loan> save(@RequestBody @Valid LoanPostRequestBody loansPostRequestBody, @PathVariable String idBook){
		return new ResponseEntity<>(serviceLoan.save(loansPostRequestBody,idBook), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when loan does not exist in the dataBase")
	})
	public ResponseEntity<Void> delete(@PathVariable long id){
		serviceLoan.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
	@PutMapping
	@Operation(description = "for the loan to be made, the user Id and the book Id are required")
	public ResponseEntity<Void> update(@RequestBody @Valid LoanPutRequestBody loansPutRequestBody){
		serviceLoan.update(loansPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

