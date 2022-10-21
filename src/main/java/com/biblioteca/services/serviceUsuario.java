package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Usuario;
import com.biblioteca.mapper.UsuarioMapper;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.requests.UsuarioPostRequestBody;
import com.biblioteca.requests.UsuarioPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceUsuario implements UserDetailsService{

	private final RepositorioUsuario userRepository;
		
	private final GetUserDetails userAuthenticated;
	
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
	
	public Usuario getMyUser() {
		return userAuthenticated.userAuthenticated();
	}
	
	@Transactional
	public Usuario saveUser(UsuarioPostRequestBody usuarioPostRequestBody) {	
		Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody);
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		usuario.setAuthorities("ROLE_USER");
	    return userRepository.save(usuario);		
	}
	
	@Transactional
	public Usuario saveUserAdmin(UsuarioPostRequestBody usuarioPostRequestBody) {	
		Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody);
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		usuario.setAuthorities("ROLE_ADMIN,ROLE_USER");
	    return userRepository.save(usuario);
	}
	
	@Transactional
	public void delete(long id) {	
		userRepository.delete(findByIdOrElseThrowResourceNotFoundException(id));		
	}
	
	@Transactional
	public Usuario update(UsuarioPutRequestBody usuarioPutRequestBody) {
		Usuario savedUsuario = userRepository.findById(userAuthenticated.userAuthenticated().getId()).get();
		Usuario usuario = UsuarioMapper.INSTANCE.updateUser(usuarioPutRequestBody, savedUsuario);
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		return userRepository.save(usuario);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.ofNullable(userRepository.findByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException("usuario not found"));
	}
}
