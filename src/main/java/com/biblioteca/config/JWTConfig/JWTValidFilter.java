package com.biblioteca.config.JWTConfig;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTValidFilter extends BasicAuthenticationFilter {

	public static final String HEADER_ATTRIBUTE = "Authorization";
	public static final String ATTRIBUTE_PREFIX = "Bearer ";

	public JWTValidFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String attribute = request.getHeader(HEADER_ATTRIBUTE);

		if (attribute == null) {
			chain.doFilter(request, response);
			return;
		}
		if (!attribute.startsWith(ATTRIBUTE_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		String token = attribute.replace(ATTRIBUTE_PREFIX, "");
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);;
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {

		DecodedJWT decode = JWT.require(Algorithm.HMAC512(JWTAutenticationFilter.TOKEN_PASSWORD)).build().verify(token);

		String userDomain = decode.getSubject();

		String[] roles = decode.getClaim("roles").asArray(String.class);

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		stream(roles).forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});

		if (userDomain == null) {
			return null;
		}

		return new UsernamePasswordAuthenticationToken(userDomain, null, authorities);
	}
}
