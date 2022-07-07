package com.biblioteca.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_autor")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of= {"id","nome"})
@ToString
public class Autor implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy = "autor")
	private Set<Livro> livros = new HashSet<>();
	
	public Autor(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	@JsonIgnore
	public Set<Livro> getLivros() {
		return livros;
	}
	
}
