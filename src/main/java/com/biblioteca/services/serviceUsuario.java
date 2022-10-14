package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class serviceUsuario implements UserDetailsService{
	
	private final RepositorioUsuario userRepository;
	
	public List<Usuario> findAllNonPageable(){
		return userRepository.findAll();
	}
	
	public Page<Usuario> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public List<Usuario> findByName(String name){
		return userRepository.findBynameContainingIgnoreCase(name);
	}
	
	public Usuario findByIdOrElseThrowResourceNotFoundException(long id) {
		return userRepository.findById(id).orElseThrow(() -> new BadRequestException("usuario not found"));
	}
	
	@Transactional
	public Usuario save(UsuarioPostRequestBody usuarioPostRequestBody) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody);
		usuario.setPassword(passwordEncoder.encode(usuarioPostRequestBody.getPassword()));
		
	    return userRepository.save(usuario);
	}
	
	public void delete(long id) {
		try {
		userRepository.delete(findByIdOrElseThrowResourceNotFoundException(id));
		}
		catch(DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Usuario update(UsuarioPutRequestBody usuarioPutRequestBody) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		Usuario savedUsuario = userRepository.findById(usuarioPutRequestBody.getId()).get();
		Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPutRequestBody);
		usuario.setId(savedUsuario.getId());
		usuario.setPassword(passwordEncoder.encode(usuarioPutRequestBody.getPassword()));
		
		return userRepository.save(usuario);
		
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.ofNullable(userRepository.findByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException("usuario not found"));
	}
}
