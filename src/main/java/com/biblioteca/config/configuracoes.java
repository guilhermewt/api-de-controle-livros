package com.biblioteca.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioUsuario;

@Configuration
public class configuracoes implements CommandLineRunner{

	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Override
	public void run(String... args) throws Exception {
		Usuario user1 = new Usuario(1l,"guilherme", "guilherme@gmail","gui","1234567");
		Usuario user2 = new Usuario(2l,"marcos", "marcos@gmail","marcos","1234567");
		Usuario user3 = new Usuario(3l,"tony", "tony@gmail","tony","1234567");
		
		repositorioUsuario.saveAll(Arrays.asList(user1,user2,user3));
		
	}

}
