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

import com.biblioteca.entities.Livro;
import com.biblioteca.services.serviceLivro;

@RestController
@RequestMapping(value = "/livros")
public class LivroResources {

	@Autowired
	private serviceLivro serviceLivro;
	
	@GetMapping
	public ResponseEntity<List<Livro>> findAll(){
		List<Livro> list = serviceLivro.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}")
	public ResponseEntity<Livro> findById(@PathVariable long id){
		Livro usuario = serviceLivro.findById(id);
		return ResponseEntity.ok().body(usuario);
	}
	
	//http://localhost:8080/livros/3/1
	@RequestMapping(path = "/{idUsuario}/{idEditora}/{idAutor}",method = RequestMethod.POST)
	public ResponseEntity<Livro> insert(@PathVariable long idUsuario,@PathVariable long idEditora,@PathVariable long idAutor, @RequestBody Livro obj){
		Livro livro = serviceLivro.insert(obj,idUsuario,idEditora,idAutor);
		return ResponseEntity.ok().body(livro);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Livro> delete(@PathVariable long id){
		serviceLivro.delete(id);
		return ResponseEntity.noContent().build();
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Livro> findById(@RequestBody Livro obj,  @PathVariable long id){
		obj.setId(id);
		Livro usuario = serviceLivro.update(obj);
		return ResponseEntity.ok().body(usuario);
	}
}
