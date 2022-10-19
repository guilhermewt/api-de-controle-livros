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

import com.biblioteca.entities.Emprestimo;

@Repository
public interface RepositorioEmprestimo extends JpaRepository<Emprestimo, Long>{
	
	List<Emprestimo> findByUsuarioId(Long id);
	
	Page<Emprestimo> findByUsuarioId(Long id, Pageable pageable);
	
	@Query("select u from Emprestimo u where u.id = ?1 and u.usuario.id = ?2")
	Optional<Emprestimo> findAuthenticatedUserById(long idBook, long idUser);
	
	@Modifying
	@Transactional
	@Query("DELETE from Emprestimo u where u.id = ?1 and u.usuario.id = ?2")
	void deleteAuthenticatedUserBookById(long idBook,long idUser);
}
