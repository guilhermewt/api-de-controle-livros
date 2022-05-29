package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long>{

}
