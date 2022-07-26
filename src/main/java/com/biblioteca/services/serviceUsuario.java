package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.requests.UsuarioPostRequestBody;
import com.biblioteca.requests.UsuarioPutRequestBody;
import com.biblioteca.services.exceptions.DatabaseException;
import com.biblioteca.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceUsuario implements UserDetailsService{
	
	private final RepositorioUsuario serviceRepository;
	
	public List<Usuario> findAll(){
		return serviceRepository.findAll();
	}
	
	public Usuario findByIdOrElseThrowResourceNotFoundException(long id) {
		return serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Usuario insert(UsuarioPostRequestBody usuarioPutRequestBody) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		Usuario usuario = Usuario.builder()
		.nome(usuarioPutRequestBody.getNome())
		.email(usuarioPutRequestBody.getEmail())
		.login(usuarioPutRequestBody.getLogin())
		.username(usuarioPutRequestBody.getUsername())
		.password(passwordEncoder.encode(usuarioPutRequestBody.getPassword()))
		.authorities(usuarioPutRequestBody.getAuthorities()).build();
		
	    return serviceRepository.save(usuario);
	}
	
	public void delete(long id) {
		try {
		serviceRepository.delete(findByIdOrElseThrowResourceNotFoundException(id));
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Usuario update(UsuarioPutRequestBody usuarioPutRequestBody) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		Usuario savedUsuario = serviceRepository.findById(usuarioPutRequestBody.getId()).get();
		Usuario usuario = Usuario.builder()
				.id(savedUsuario.getId())
				.nome(usuarioPutRequestBody.getNome())
				.email(usuarioPutRequestBody.getEmail())
				.login(usuarioPutRequestBody.getLogin())
				.username(usuarioPutRequestBody.getUsername())
				.password(passwordEncoder.encode(usuarioPutRequestBody.getPassword()))
				.authorities(usuarioPutRequestBody.getAuthorities()).build();
		
		return serviceRepository.save(usuario);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.ofNullable(serviceRepository.findByusername(username))
				.orElseThrow(() -> new UsernameNotFoundException("usuario not found"));
	}
}
