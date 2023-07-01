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

import com.biblioteca.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	
	Page<Book> findByUserDomainId(long UserDomainId,Pageable pageable);
		
	List<Book> findByUserDomainId(long UserDomainId);
	
    @Query("select u from Book u where lower(u.title) like lower(concat('%', ?1,'%')) and u.userDomain.id=?2")
    List<Book> findAuthenticatedUserBooksByTitle(String name,long userDomainId);
	
	@Query("select u from Book u where u.id = ?1 and u.userDomain.id = ?2")
	Optional<Book> findAuthenticatedUserBooksById(Long idBook,long UserDomainId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Book u where u.id = ?1 and u.userDomain.id = ?2")
    void deleteAuthenticatedUserBookById(Long idBook,long userDomainId);
	
}
