package com.biblioteca.resources;

import java.util.List;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.biblioteca.mapper.UserDomainMapper;
import com.biblioteca.requests.UserDomainGetRequestBody;
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
	public ResponseEntity<List<UserDomainGetRequestBody>> findAllNonPageable() {
		return ResponseEntity.ok(UserDomainMapper.INSTANCE.toListOfUserDomainGetRequestBody(serviceUserDomain.findAllNonPageable()));
	}
	
	@GetMapping(value = "/admin")
	@Operation(summary = "find all User pageable - only users admin are allowed", description = "the default size is 20, use the parameter to change the default value ")
	public ResponseEntity<Page<UserDomainGetRequestBody>> findAll(@ParameterObject Pageable pageable) {
		PageImpl<UserDomainGetRequestBody> listPage = new PageImpl<>(
				UserDomainMapper.INSTANCE.toListOfUserDomainGetRequestBody(serviceUserDomain.findAll(pageable).toList()));
		return ResponseEntity.ok(listPage);
	}
	
	
	@GetMapping(value = "/admin/findname")
	@Operation(summary = "find all user by name - only users admin are allowed")
	public ResponseEntity<List<UserDomainGetRequestBody>> findByName(@RequestParam String name) {
		return ResponseEntity.ok(UserDomainMapper.INSTANCE.toListOfUserDomainGetRequestBody(serviceUserDomain.findByName(name)));
	}

	@GetMapping(value = "/admin/{id}")
	@Operation(summary = "find users by id - only users admin are allowed")
	public ResponseEntity<UserDomainGetRequestBody> findById(@PathVariable long id) {
		return ResponseEntity.ok(UserDomainMapper.INSTANCE.toUserDomainGetRequestBody(serviceUserDomain.findByIdOrElseThrowResourceNotFoundException(id)));
	}
	
	@GetMapping(value = "/users-logged")
	@Operation(summary = "logged in user")
	public ResponseEntity<UserDomainGetRequestBody> getMyUser() {
		return ResponseEntity.ok(UserDomainMapper.INSTANCE.toUserDomainGetRequestBody(serviceUserDomain.getMyUser()));
	}

	@PostMapping(value = "/save")
	@Operation(summary = "endpoint to register and have access to other methods")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "successful operation"),
			@ApiResponse(responseCode = "409", description = "when user already exists in the database")
	})
	public ResponseEntity<UserDomainGetRequestBody> saveUser(@RequestBody @Valid UserDomainPostRequestBody userDomainPostRequestBody) {	
		UserDomain userDomain = serviceUserDomain.saveUser(UserDomainMapper.INSTANCE.toUserDomain(userDomainPostRequestBody));
		
		return new ResponseEntity<>(UserDomainMapper.INSTANCE.toUserDomainGetRequestBody(userDomain),
				HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/admin/saveAdmin")
	@Operation(summary = "only users admin are allowed")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "successful operation"),
			@ApiResponse(responseCode = "409", description = "when user already exists in the database")
	})
	public ResponseEntity<UserDomainGetRequestBody> saveUserAdmin(@RequestBody @Valid UserDomainPostRequestBody userDomainPostRequestBody) {
		UserDomain userDomain = serviceUserDomain.saveUserAdmin(UserDomainMapper.INSTANCE.toUserDomain(userDomainPostRequestBody));
		
		return new ResponseEntity<>(UserDomainMapper.INSTANCE.toUserDomainGetRequestBody(userDomain),
				HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/admin/{id}")
	@Operation(summary = "only users admin are allowed")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when user does not exist in the dataBase")
	})
	public ResponseEntity<Void> delete(@PathVariable long id) {
		serviceUserDomain.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping
	@Operation(summary = "update my user", description = "only your username will be updated")
	public ResponseEntity<Void> update(@RequestBody UserDomainPutRequestBody userDomainPutRequestBody) {
		serviceUserDomain.update(userDomainPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/update-password")
	@Operation(summary = "update user password")
	public ResponseEntity<Void> updatePassword(@RequestParam  String oldPassword, String newPassword){
		serviceUserDomain.updatePassword(oldPassword, newPassword);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
