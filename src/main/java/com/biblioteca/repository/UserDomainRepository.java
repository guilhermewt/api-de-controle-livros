package com.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.UserDomain;

@Repository
public interface UserDomainRepository extends JpaRepository<UserDomain, Long>{
	
	Optional<UserDomain> findByUsername(String username);
	List<UserDomain> findBynameContainingIgnoreCase(String name);
}
