package com.biblioteca.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		passwordEncoder.encode("biblioteca");
		
		auth.inMemoryAuthentication()
		.withUser("guilherme")
		.password(passwordEncoder.encode("biblioteca"))
		.roles("ADMIN","USER")
		.and()
		.withUser("userBiblioteca")
		.password(passwordEncoder.encode("biblioteca"))
		.roles("User");
	}
}
