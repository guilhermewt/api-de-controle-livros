package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Usuario;
import com.biblioteca.mapper.UsuarioMapper;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.requests.UsuarioPostRequestBody;
import com.biblioteca.requests.UsuarioPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceUsuario implements UserDetailsService{
	
	private final RepositorioUsuario serviceRepository;
	
	public List<Usuario> findAllNonPageable(){
		return serviceRepository.findAll();
	}
	
	public Page<Usuario> findAll(Pageable pageable) {
		return serviceRepository.findAll(pageable);
	}
	
	public List<Usuario> findByName(String name){
		return serviceRepository.findBynameContainingIgnoreCase(name);
	}
	
	public Usuario findByIdOrElseThrowResourceNotFoundException(long id) {
		return serviceRepository.findById(id).orElseThrow(() -> new BadRequestException("usuario not found"));
	}
	
	@Transactional
	public Usuario save(UsuarioPostRequestBody usuarioPostRequestBody) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody);
		usuario.setPassword(passwordEncoder.encode(usuarioPostRequestBody.getPassword()));
		
	    return serviceRepository.save(usuario);
	}
	
	public void delete(long id) {
		try {
		serviceRepository.delete(findByIdOrElseThrowResourceNotFoundException(id));
		}
		catch(DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Usuario update(UsuarioPutRequestBody usuarioPutRequestBody) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		Usuario savedUsuario = serviceRepository.findById(usuarioPutRequestBody.getId()).get();
		Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPutRequestBody);
		usuario.setId(savedUsuario.getId());
		usuario.setPassword(passwordEncoder.encode(usuarioPutRequestBody.getPassword()));
		
		return serviceRepository.save(usuario);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.ofNullable(serviceRepository.findByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException("usuario not found"));
	}
}
