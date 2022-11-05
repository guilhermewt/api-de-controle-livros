package com.biblioteca.services;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Usuario;
import com.biblioteca.mapper.UsuarioMapper;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.repository.RoleModelRepository;
import com.biblioteca.requests.UsuarioPostRequestBody;
import com.biblioteca.requests.UsuarioPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class serviceUsuario implements UserDetailsService{
	
	private final RepositorioUsuario userRepository;
		
	private final GetUserDetails userAuthenticated;
	
	private final RoleModelRepository roleModelRepository;
	
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
	

	public Usuario saveUser(UsuarioPostRequestBody usuarioPostRequestBody) {	
	   	log.info(roleModelRepository.findById(2l).get());

		Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody);
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));		
		usuario.getRoles().add(roleModelRepository.findById(2l).get());	    
		return userRepository.save(usuario);		
	}
	
	public Usuario saveUserAdmin(UsuarioPostRequestBody usuarioPostRequestBody) {	
		Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody);
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		usuario.getRoles().addAll(Arrays.asList(roleModelRepository.findById(1l).get(),roleModelRepository.findById(2l).get()));
	    return userRepository.save(usuario);
	}
	
	public void delete(long id) {	
		userRepository.delete(findByIdOrElseThrowResourceNotFoundException(id));		
	}
	
	public Usuario update(UsuarioPutRequestBody usuarioPutRequestBody) {
		Usuario savedUsuario = userRepository.findById(userAuthenticated.userAuthenticated().getId()).get();
		Usuario usuario = UsuarioMapper.INSTANCE.updateUser(usuarioPutRequestBody, savedUsuario);
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		return userRepository.save(usuario);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("usuario not found"));
		
		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
	}
}
