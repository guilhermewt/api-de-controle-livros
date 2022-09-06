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

import com.biblioteca.entities.Usuario;
import com.biblioteca.requests.UsuarioPostRequestBody;
import com.biblioteca.requests.UsuarioPutRequestBody;
import com.biblioteca.services.serviceUsuario;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/usuarios")
@RequiredArgsConstructor
public class UsuarioResources {

	private final serviceUsuario serviceUsuario;

	@GetMapping
	public ResponseEntity<List<Usuario>> findAllNonPageable() {
		return ResponseEntity.ok(serviceUsuario.findAllNonPageable());
	}
	
	@GetMapping
	public ResponseEntity<Page<Usuario>> findAll(Pageable pageable) {
		return ResponseEntity.ok(serviceUsuario.findAll(pageable));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable long id) {
		return ResponseEntity.ok(serviceUsuario.findByIdOrElseThrowResourceNotFoundException(id));
	}

	@PostMapping
	public ResponseEntity<Usuario> save(@RequestBody @Valid UsuarioPostRequestBody usuarioPostRequestBody) {
		return new ResponseEntity<>(serviceUsuario.save(usuarioPostRequestBody),HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{id}")
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
