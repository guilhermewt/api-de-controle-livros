package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.Emprestimo;

@Repository
public interface RepositorioEmprestimo extends JpaRepository<Emprestimo, Long>{

}
