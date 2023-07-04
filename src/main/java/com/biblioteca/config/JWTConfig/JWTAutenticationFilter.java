package com.biblioteca.config.JWTConfig;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.biblioteca.data.JwtObject;
import com.biblioteca.data.UserDomainDetails;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.services.exceptions.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAutenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final int TOKEN_EXPIRATION = 86400000;
	public static final String TOKEN_PASSWORD = "463408a1-54c9-4307-bb1c-6cced559f5a7";

	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UserDomain userDomain = new ObjectMapper().readValue(request.getInputStream(), UserDomain.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDomain.getUsername(), userDomain.getPassword(),new ArrayList<>()));
		} catch (IOException e) {
			throw new BadRequestException("failed to authenticate the user" + e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		    
		    UserDomainDetails userDomainDetails = (UserDomainDetails) authResult.getPrincipal();
		
		    String token = JWT.create()
		    				.withSubject(userDomainDetails.getUsername())
		    				.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
		    				.withClaim("roles", userDomainDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
		    				.sign(Algorithm.HMAC512(TOKEN_PASSWORD));
		    response.setContentType(APPLICATION_JSON_VALUE);
		    new ObjectMapper().writeValue(response.getOutputStream(),new JwtObject(userDomainDetails.getUsername(),token));
		    		
		    		
		    		super.successfulAuthentication(request, response, chain, authResult);
	}
}
