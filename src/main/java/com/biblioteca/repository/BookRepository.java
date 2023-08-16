package com.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.Book;
import com.biblioteca.enums.StatusBook;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	
	Page<Book> findByUserDomainIdOrderByIdDesc(long UserDomainId,Pageable pageable);
	
	Page<Book> findByUserDomainIdAndStatusBookOrderByIdDesc(long UserDomainId,StatusBook statusBook,Pageable pageable);
		
	List<Book> findByUserDomainIdOrderByIdDesc(long UserDomainId);
	
	List<Book> findByUserDomainId(long UserDomainId);
	
	Page<Book> findByUserDomainIdAndAuthorsContainingIgnoreCaseOrderByIdDesc(long UserDomainId,String authors,Pageable pageable);
	
	Page<Book> findByUserDomainIdAndGenrersNameContainingIgnoreCaseOrderByIdDesc(long UserDomainId,String genrer,Pageable pageable);
	
    //@Query("select u from Book u where lower(u.title) like lower(concat('%', ?1,'%')) and u.userDomain.id=?2")
    Page<Book> findByUserDomainIdAndTitleContainingIgnoreCaseOrderByIdDesc(long userDomainId,String title,Pageable pageable);
	
	//@Query("select u from Book u where u.id = ?1 and u.userDomain.id = ?2")
	Optional<Book> findByIdAndUserDomainId(Long idBook,long UserDomainId);
	
	@Transactional
    void deleteByIdAndUserDomainId(Long id, Long userDomainId);
	
}
