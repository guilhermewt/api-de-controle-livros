package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.services.exceptions.ResourceNotFoundException;

@Service
public class serviceUsuario implements UserDetailsService{
	
	@Autowired
	private RepositorioUsuario serviceRepository;
	
	
	public List<Usuario> findAll(){
		return serviceRepository.findAll();
	}
	
	public Usuario findById(long id) {
		Optional<Usuario> usuario = serviceRepository.findById(id);
		return usuario.orElseThrow(() -> new ResourceNotFoundException(id)); 
	}
	
	public Usuario insert(Usuario obj) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));
		return serviceRepository.save(obj);
	}
	
	public void delete(long id) {
		findById(id);
		serviceRepository.deleteById(id);
	}
	
	public Usuario update(Usuario obj) {
		Usuario usuario = serviceRepository.findById(obj.getId()).get();
		updateData(usuario,obj);
		return serviceRepository.save(usuario);
	}

	private void updateData(Usuario usuario, Usuario obj) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		usuario.setNome(obj.getNome());
		usuario.setEmail(obj.getEmail());
		usuario.setLogin(obj.getLogin());
		usuario.setUsername(obj.getUsername());
		usuario.setPassword(passwordEncoder.encode(obj.getPassword()));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.ofNullable(serviceRepository.findByusername(username))
				.orElseThrow(() -> new UsernameNotFoundException("usuario not found"));
	}
}
