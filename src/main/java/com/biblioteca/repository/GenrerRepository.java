package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.Genrer;

@Repository
public interface GenrerRepository extends JpaRepository<Genrer, Long>{
	
}
