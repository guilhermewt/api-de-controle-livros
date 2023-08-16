package com.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.biblioteca.config.JWTConfig.JWTAutenticationFilter;
import com.biblioteca.config.JWTConfig.JWTValidFilter;

@Configuration
public class SecurityConfig{

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf().disable()
		//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/userDomains/save").permitAll()
		.antMatchers("/userDomains/admin/**").hasRole("ADMIN")
		.antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**","/","/booktest/save","/h2-console/**").permitAll()
		.antMatchers(HttpMethod.POST, "/login").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.headers().frameOptions().sameOrigin()
		.and()
		.addFilter(new JWTValidFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))))
		.addFilter(new JWTAutenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
	     throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
