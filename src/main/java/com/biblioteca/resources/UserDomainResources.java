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

import com.biblioteca.entities.UserDomain;
import com.biblioteca.requests.UserDomainPostRequestBody;
import com.biblioteca.requests.UserDomainPutRequestBody;
import com.biblioteca.services.UserDomainService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/userDomains")
@RequiredArgsConstructor
public class UserDomainResources {

	private final UserDomainService serviceUserDomain;
	
	@GetMapping(value = "/admin/all")
	@Operation(summary = "find all user non pageable - only users admin are allowed")
	public ResponseEntity<List<UserDomain>> findAllNonPageable() {
		return ResponseEntity.ok(serviceUserDomain.findAllNonPageable());
	}
	
	@GetMapping(value = "/admin")
	@Operation(summary = "find all User pageable - only users admin are allowed", description = "the default size is 20, use the parameter to change the default value ")
	public ResponseEntity<Page<UserDomain>> findAll(@ParameterObject Pageable pageable) {
		return ResponseEntity.ok(serviceUserDomain.findAll(pageable));
	}
	
	@GetMapping(value = "/admin/findname")
	@Operation(summary = "find all user by name - only users admin are allowed")
	public ResponseEntity<List<UserDomain>> findByName(@RequestParam String name) {
		return ResponseEntity.ok(serviceUserDomain.findByName(name));
	}

	@GetMapping(value = "/admin/{id}")
	@Operation(summary = "find users by id - only users admin are allowed")
	public ResponseEntity<UserDomain> findById(@PathVariable long id) {
		return ResponseEntity.ok(serviceUserDomain.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	@GetMapping(value = "/users-logged")
	@Operation(summary = "logged in user")
	public ResponseEntity<UserDomain> getMyUser() {
		return ResponseEntity.ok(serviceUserDomain.getMyUser());
	}

	@PostMapping(value = "/save")
	@Operation(summary = "endpoint to register and have access to other methods")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "successful operation"),
			@ApiResponse(responseCode = "409", description = "when user already exists in the database")
	})
	public ResponseEntity<UserDomain> saveUser(@RequestBody @Valid UserDomainPostRequestBody userDomainPostRequestBody) {
		return new ResponseEntity<>(serviceUserDomain.saveUser(userDomainPostRequestBody),HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/admin/saveAdmin")
	@Operation(summary = "only users admin are allowed")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "successful operation"),
			@ApiResponse(responseCode = "409", description = "when user already exists in the database")
	})
	public ResponseEntity<UserDomain> saveUserAdmin(@RequestBody @Valid UserDomainPostRequestBody userDomainPostRequestBody) {
		return new ResponseEntity<>(serviceUserDomain.saveUserAdmin(userDomainPostRequestBody),HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/admin/{id}")
	@Operation(summary = "only users admin are allowed")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when user does not exist in the dataBase")
	})
	public ResponseEntity<UserDomain> delete(@PathVariable long id) {
		serviceUserDomain.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping
	@Operation(summary = "update my user", description = "only your username will be updated")
	public ResponseEntity<UserDomain> update(@RequestBody UserDomainPutRequestBody userDomainPutRequestBody) {
		serviceUserDomain.update(userDomainPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
