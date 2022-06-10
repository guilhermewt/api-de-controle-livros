package com.biblioteca.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.services.serviceEmprestimo;

@RestController
@RequestMapping(value = "/emprestimos")
public class EmprestimoResources {

	@Autowired
	private serviceEmprestimo serviceEmprestimo;
	
	@GetMapping
	public ResponseEntity<List<Emprestimo>> findAll(){
		List<Emprestimo> list = serviceEmprestimo.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}")
	public ResponseEntity<Emprestimo> findById(@PathVariable long id){
		Emprestimo usuario = serviceEmprestimo.findById(id);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(path = "/{id}",method = RequestMethod.POST)
	public ResponseEntity<Emprestimo> insert(@PathVariable long id,  @RequestBody Emprestimo obj){
		Emprestimo usuario = serviceEmprestimo.insert(obj,id);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Emprestimo> delete(@PathVariable long id){
		serviceEmprestimo.delete(id);
		return ResponseEntity.noContent().build();
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Emprestimo> findById(@RequestBody Emprestimo obj,  @PathVariable long id){
		obj.setId(id);
		Emprestimo usuario = serviceEmprestimo.update(obj);
		return ResponseEntity.ok().body(usuario);
	}
}
