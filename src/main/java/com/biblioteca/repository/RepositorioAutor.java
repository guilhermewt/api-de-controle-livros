package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.Autor;

@Repository
public interface RepositorioAutor extends JpaRepository<Autor, Long>{

}
