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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class serviceLivro {

	private final RepositorioLivro livroRepositorio;

	private final RepositorioEditora editoraRepositorio;

	private final RepositorioUsuario UsuarioRepositorio;

	private final RepositorioAutor autorRepositorio;

	public List<Livro> findAllNonPageable() {
		return livroRepositorio.findAll();
	}
	
	public Page<Livro> findAll(Pageable pageable) {
		return livroRepositorio.findAll(pageable);
	}

	public Livro findByIdOrElseThrowResourceNotFoundException(long id) {
		return  livroRepositorio.findById(id).orElseThrow(() -> new BadRequestException("livro not founnd"));
	}

	@Transactional
	public Livro save(LivroPostRequestBody livroPostRequestBody, long idUsuario, long idEditora, long idAutor) {
		Usuario user = UsuarioRepositorio.findById(idUsuario).get();
		Editora editora = editoraRepositorio.findById(idEditora).get();
		Autor autor = autorRepositorio.findById(idAutor).get();
		
		Livro livro = LivroMapper.INSTANCE.toLivro(livroPostRequestBody);
		
		livro.setUsuario(user);
		livro.setEditora(editora);
		livro.setAutor(autor);
		return livroRepositorio.save(livro);
	}

	public void delete(long id) {
		try {
			livroRepositorio.delete(findByIdOrElseThrowResourceNotFoundException(id));
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public void update(LivroPutRequestBody livroPutRequestBody) {
		Livro livroSaved = livroRepositorio.findById(livroPutRequestBody.getId()).get();
		Livro livro = LivroMapper.INSTANCE.toLivro(livroPutRequestBody);
		livro.setId(livroSaved.getId());
		livroRepositorio.save(livro);
	}
}
