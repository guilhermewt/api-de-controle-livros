package com.biblioteca.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.mapper.LivroMapper;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.requests.LivroPostRequestBody;
import com.biblioteca.requests.LivroPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceLivro {

	private final RepositorioLivro livroRepositorio;

	private final RepositorioUsuario UsuarioRepositorio;
 
	private final GetUserDetails userAuthenticated;
	
	public List<Livro> findAllNonPageable() {
		return livroRepositorio.findByUsuarioId(userAuthenticated.userAuthenticated().getId());
	}
	
	public Page<Livro> findAll(Pageable pageable) {
		return livroRepositorio.findByUsuarioId(userAuthenticated.userAuthenticated().getId(), pageable);
	}

	public Livro findByIdOrElseThrowResourceNotFoundException(long idBook) {
		return  livroRepositorio.findAuthenticatedUserBooksById(idBook, userAuthenticated.userAuthenticated().getId())
				.orElseThrow(() -> new BadRequestException("livro not found"));
	}
	
	public List<Livro> findByTitulo(String title){
		return livroRepositorio.findAuthenticatedUserBooksByTitle(title,userAuthenticated.userAuthenticated().getId());
	}

	@Transactional
	public Livro save(LivroPostRequestBody livroPostRequestBody) {
		Usuario user = UsuarioRepositorio.findById(userAuthenticated.userAuthenticated().getId()).get();
		
		Livro livro = LivroMapper.INSTANCE.toLivro(livroPostRequestBody);
		livro.setUsuario(user);		
		return livroRepositorio.save(livro);
	}
	
	@Transactional
	public void delete(long idBook) {
		try {
			livroRepositorio.deleteAuthenticatedUserBookById(findByIdOrElseThrowResourceNotFoundException(idBook)
					.getId(),userAuthenticated.userAuthenticated()
					.getId());
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	@Transactional
	public void update(LivroPutRequestBody livroPutRequestBody) {
		Usuario user = UsuarioRepositorio.findById(userAuthenticated.userAuthenticated().getId()).get();

		
		Livro livroSaved = livroRepositorio.findAuthenticatedUserBooksById(livroPutRequestBody.getId(), 
				userAuthenticated.userAuthenticated()
				.getId())
				.orElseThrow(() -> new BadRequestException("livro not found"));
		
		
		Livro livro = LivroMapper.INSTANCE.toLivro(livroPutRequestBody);
		livro.setId(livroSaved.getId());
		livro.setUsuario(user);	
		livroRepositorio.save(livro);
	}
}
