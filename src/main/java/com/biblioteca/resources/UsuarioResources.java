package com.biblioteca.resources;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.Usuario;
import com.biblioteca.requests.UsuarioPostRequestBody;
import com.biblioteca.requests.UsuarioPutRequestBody;
import com.biblioteca.services.serviceUsuario;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "usuarios/admin")
@RequiredArgsConstructor
@Log4j2
public class UsuarioResources {

	private final serviceUsuario serviceUsuario;
	
	
	@GetMapping(value = "/all")
	@Operation(summary = "find all user non pageable")
	public ResponseEntity<List<Usuario>> findAllNonPageable() {
		return ResponseEntity.ok(serviceUsuario.findAllNonPageable());
	}
	
	@GetMapping
	@Operation(summary = "find all User pageable", description = "the default size is 20, use the parameter to change the default value ")
	public ResponseEntity<Page<Usuario>> findAll(@ParameterObject Pageable pageable) {
		return ResponseEntity.ok(serviceUsuario.findAll(pageable));
	}
	
	@GetMapping(value = "/findname")
	@Operation(summary = "find all user by name")
	public ResponseEntity<List<Usuario>> findByName(@RequestParam String name) {
		return ResponseEntity.ok(serviceUsuario.findByName(name));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable long id) {
		return ResponseEntity.ok(serviceUsuario.findByIdOrElseThrowResourceNotFoundException(id));
	}

	@PostMapping(value = "/save")
	public ResponseEntity<Usuario> save(@RequestBody @Valid UsuarioPostRequestBody usuarioPostRequestBody) {
		return new ResponseEntity<>(serviceUsuario.save(usuarioPostRequestBody),HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when user does not exist in the dataBase")
	})
	public ResponseEntity<Usuario> delete(@PathVariable long id) {
		serviceUsuario.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping
	public ResponseEntity<Usuario> update(@RequestBody UsuarioPutRequestBody usuarioPutRequestBody) {
		serviceUsuario.update(usuarioPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
