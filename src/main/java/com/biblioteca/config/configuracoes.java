package com.biblioteca.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.biblioteca.entities.Autor;
import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.RepositorioAutor;
import com.biblioteca.repository.RepositorioEditora;
import com.biblioteca.repository.RepositorioEmprestimo;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;

@Configuration
public class configuracoes implements CommandLineRunner{

	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private RepositorioLivro repositorioLivro;
	
	@Autowired
	private RepositorioEmprestimo repositorioEmprestimo;
	
	@Autowired
	private RepositorioEditora repositorioEditora;
	
	@Autowired
	private RepositorioAutor repositorioAutor; 
	
	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Usuario user1 = new Usuario(null,"rafael silva","rafa@gmail.com","rafa12","rafasilva","{bcrypt}$2a$10$gUqmuiSjMuhIRW6T8IQWAOy/IcyNPj0yMyWBBd3g1HWQRa3FQkFeW","ROLE_ADMIN,ROLE_USER");
		Usuario user2 = new Usuario(null, "marcos pereira","marcos@gmail.com", "marcos87","marcospereira","{bcrypt}$2a$10$gUqmuiSjMuhIRW6T8IQWAOy/IcyNPj0yMyWBBd3g1HWQRa3FQkFeW","ROLE_USER");
		Usuario user3 = new Usuario(null, "tony roque","tony@gmail.com", "tony","tonyroque","{bcrypt}$2a$10$gUqmuiSjMuhIRW6T8IQWAOy/IcyNPj0yMyWBBd3g1HWQRa3FQkFeW","ROLE_USER");
		
		/*
		Usuario user1 = new Usuario(1l,"rafael silva", "rafa@gmail","rafa silva","123");
		Usuario user2 = new Usuario(2l,"marcos", "marcos@gmail","marcos","1234567");
		Usuario user3 = new Usuario(3l,"tony", "tony@gmail","tony","1234567");
		*/
		
		repositorioUsuario.saveAll(Arrays.asList(user1,user2,user3));
		
		Editora edit1 = new Editora(1l,"editora rocco");
		Editora edit2 = new Editora(2l,"saraiva");
		
		repositorioEditora.saveAll(Arrays.asList(edit1,edit2));
		
		Autor autor = new Autor(1l,"nelson mandela");
		
		repositorioAutor.save(autor);
		
		Livro lv1 = new Livro(1l,"the lord of the kings" , sdf.parse("2009/05/26"));
		Livro lv2 = new Livro(2l,"o poder da acao" , sdf.parse("2012/04/01"));
			
		lv1.setUsuario(user1);
		lv2.setUsuario(user2);
		
		lv1.setEditora(edit2);
		lv2.setEditora(edit1);
		
		lv1.setAutor(autor);
		lv2.setAutor(autor);
 		
		repositorioLivro.saveAll(Arrays.asList(lv1,lv2));
		
		Emprestimo emp1 = new Emprestimo(1l, sdf.parse("2022/05/22"), sdf.parse("2022/06/26"));
		emp1.setUsuario(user1);
		emp1.getLivros().add(lv1);
		
		repositorioEmprestimo.save(emp1);
		
	}

}
