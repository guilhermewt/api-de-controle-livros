package com.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.Livro;

@Repository
public interface RepositorioLivro extends JpaRepository<Livro, Long>{
	
	Page<Livro> findByUsuarioId(long idUser,Pageable pageable);
		
	List<Livro> findByUsuarioId(long idUser);
	
    @Query("select u from Livro u where lower(u.titulo) like lower(concat('%', ?1,'%')) and u.usuario.id=?2")
    List<Livro> findAuthenticatedUserBooksByTitle(String name,long idUser);
	
	@Query("select u from Livro u where u.id = ?1 and u.usuario.id = ?2")
	Optional<Livro> findAuthenticatedUserBooksById(long idBook,long idUser);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Livro u where u.id = ?1 and u.usuario.id = ?2")
    void deleteAuthenticatedUserBookById(long idBook,long idUser);
	
}
