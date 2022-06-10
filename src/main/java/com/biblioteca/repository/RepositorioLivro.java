package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.Livro;

@Repository
public interface RepositorioLivro extends JpaRepository<Livro, Long>{

}
