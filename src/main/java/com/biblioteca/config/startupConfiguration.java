package com.biblioteca.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.biblioteca.entities.Genrer;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.repository.GenrerRepository;
import com.biblioteca.repository.UserDomainRepository;

@Configuration
public class startupConfiguration implements CommandLineRunner{

		@Autowired
		private UserDomainRepository userRepository;
		
		@Autowired
		private GenrerRepository genrer;
		
		
		@Override
		public void run(String... args) throws Exception {
			
//			userRepository.save(new UserDomain(null, "guilherme", "gui@", "guilherme", "$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6"));
//					
			 List<Genrer> genres = new ArrayList<>();
		        genres.add(new Genrer(null,"Ficção científica"));
		        genres.add(new Genrer(null,"Fantasia"));
		        genres.add(new Genrer(null,"Romance"));
		        genres.add(new Genrer(null,"Mistério"));
		        genres.add(new Genrer(null,"Suspense"));
		        genres.add(new Genrer(null,"Terror"));
		        genres.add(new Genrer(null,"Aventura"));
		        genres.add(new Genrer(null,"História"));
		        genres.add(new Genrer(null,"Biografia"));
		        genres.add(new Genrer(null, "Autoajuda"));
		        genres.add(new Genrer(null, "Policial"));
		        genres.add(new Genrer(null, "Drama"));
		        genres.add(new Genrer(null, "Comédia"));
		        genres.add(new Genrer(null, "Poesia"));
		        genres.add(new Genrer(null, "Ensaios"));
		        genres.add(new Genrer(null, "Ficção histórica"));
		        genres.add(new Genrer(null, "Ficção distópica"));
		        genres.add(new Genrer(null, "Literatura clássica"));
		        genres.add(new Genrer(null, "Literatura contemporânea"));
		        genres.add(new Genrer(null, "Literatura infantojuvenil"));
		        genres.add(new Genrer(null, "Ficção científica distópica"));
		        genres.add(new Genrer(null, "Thriller psicológico"));
		        genres.add(new Genrer(null, "Romance histórico"));
		        genres.add(new Genrer(null, "Suspense policial"));
		        genres.add(new Genrer(null, "Literatura de viagem"));
		        genres.add(new Genrer(null, "Ficção especulativa"));
		        genres.add(new Genrer(null, "Ficção de guerra"));
		        genres.add(new Genrer(null, "Livros de autoajuda financeira"));
		        genres.add(new Genrer(null, "Ficção científica pós-apocalíptica"));
		        
		        genrer.saveAll(genres);
		}
		
	}