package com.biblioteca.services.utilService;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
	 Authentication getAuthentication();
}
