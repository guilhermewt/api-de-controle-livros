package com.biblioteca.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Autor;
import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.mapper.LivroMapper;
import com.biblioteca.repository.RepositorioAutor;
import com.biblioteca.repository.RepositorioEditora;
import com.biblioteca.repository.RepositorioLivro;
import com.biblioteca.repository.RepositorioUsuario;
import com.biblioteca.requests.LivroPostRequestBody;
import com.biblioteca.requests.LivroPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class serviceLivro {

	private final RepositorioLivro livroRepositorio;

	private final RepositorioEditora editoraRepositorio;

	private final RepositorioUsuario UsuarioRepositorio;

	private final RepositorioAutor autorRepositorio;
    
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
	public Livro save(LivroPostRequestBody livroPostRequestBody, long idEditora, long idAutor) {
		Usuario user = UsuarioRepositorio.findById(userAuthenticated.userAuthenticated().getId()).get();
		Editora editora = editoraRepositorio.findById(idEditora).get();
		Autor autor = autorRepositorio.findById(idAutor).get();
		
		Livro livro = LivroMapper.INSTANCE.toLivro(livroPostRequestBody);
		
		livro.setUsuario(user);
		livro.setEditora(editora);
		livro.setAutor(autor);
		
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
	public void update(LivroPutRequestBody livroPutRequestBody, long idEditora, long idAutor) {
		Usuario user = UsuarioRepositorio.findById(userAuthenticated.userAuthenticated().getId()).get();
		Editora editora = editoraRepositorio.findById(idEditora).get();
		Autor autor = autorRepositorio.findById(idAutor).get();
		
		Livro livroSaved = livroRepositorio.findAuthenticatedUserBooksById(livroPutRequestBody.getId(), 
				userAuthenticated.userAuthenticated()
				.getId())
				.orElseThrow(() -> new BadRequestException("livro not found"));
		
		
		Livro livro = LivroMapper.INSTANCE.toLivro(livroPutRequestBody);
		livro.setId(livroSaved.getId());
		livro.setUsuario(user);
		livro.setEditora(editora);
		livro.setAutor(autor);
		
		livroRepositorio.save(livro);
	}
}
