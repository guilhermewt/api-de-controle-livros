package com.biblioteca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.biblioteca.services.serviceUsuario;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private serviceUsuario usuarioService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.authorizeRequests()
		.antMatchers("/usuarios/admin/**").hasRole("ADMIN")
		.antMatchers("/usuarios/**").hasRole("USER")
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
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
		
		auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder);
	}
}
