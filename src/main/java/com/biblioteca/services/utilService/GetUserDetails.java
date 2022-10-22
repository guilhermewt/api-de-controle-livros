package com.biblioteca.services.utilService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserDetails {

	private final RepositorioUsuario usuarioRepositorio;

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	public Usuario userAuthenticated() {
		Authentication authentication = authenticationFacade.getAuthentication();
		return Optional.ofNullable(usuarioRepositorio.findByUsername(authentication.getName()))
				.orElseThrow(() -> new BadRequestException("user not found"));
	}
}
