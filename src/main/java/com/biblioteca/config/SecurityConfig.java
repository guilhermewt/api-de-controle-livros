package com.biblioteca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.biblioteca.services.serviceUsuario;

//somente ADMIN pode mecher na url usuarios, tenta abrir uma porta para users se cadastrar e nao deixa o mesmo criar um admin


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private serviceUsuario usuarioService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/usuarios/admin/save").permitAll()
		.antMatchers("/usuarios/admin/**").hasRole("ADMIN")
		.antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**","/","/h2-console/**").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.headers().frameOptions().sameOrigin() //liberar o h2-console
		.and()
		.formLogin()
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
		.roles("USER");
		
		auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder);
	}
	
 
}
