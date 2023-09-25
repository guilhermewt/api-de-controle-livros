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

import com.biblioteca.entities.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>{
	
	List<Loan> findByUserDomainId(Long UserDomainId);
	
	Page<Loan> findByUserDomainId(Long UserDomainId, Pageable pageable);
	
	@Query("select u from Loan u where u.books.id = ?1 and u.userDomain.id = ?2")
	Optional<Loan> findAuthenticatedUserById(long idBook, long idUser);
	
	@Modifying
	@Transactional
	@Query("DELETE from Loan u where u.id = ?1 and u.userDomain.id = ?2")
	void deleteAuthenticatedUserLoanById(long idBook,long idUserDomain);
	
	Optional<Loan>findByIdAndUserDomainId(long id,long userId);
}
