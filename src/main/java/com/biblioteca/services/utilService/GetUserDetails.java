package com.biblioteca.services.utilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioUsuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class GetUserDetails {

	private final RepositorioUsuario usuarioRepositorio;

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	public Usuario userAuthenticated() {
		Authentication authentication = authenticationFacade.getAuthentication();
		return usuarioRepositorio.findByUsername(authentication.getName());
	}
}
