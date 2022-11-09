package com.biblioteca.services.utilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.UserDomain;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserDetails {

	private final UserDomainRepository usuarioRepositorio;

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	public UserDomain userAuthenticated() {
		Authentication authentication = authenticationFacade.getAuthentication();
		return usuarioRepositorio.findByUsername(authentication.getName())
				.orElseThrow(() -> new BadRequestException("user not found"));
	}
}
