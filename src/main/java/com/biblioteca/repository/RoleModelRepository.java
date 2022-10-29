package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.RoleModel;

@Repository
public interface RoleModelRepository extends JpaRepository<RoleModel, Long>{
	
	RoleModel findByRoleName(String name);
}
