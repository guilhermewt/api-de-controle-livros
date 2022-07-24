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

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_editora")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of= {"id","nome"})
@SuperBuilder
public class Editora implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy = "editora")
	@Builder.Default
	private Set<Livro> livros = new HashSet<>();
	
	public Editora(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	@JsonIgnore
	public Set<Livro> getLivros() {
		return livros;
	}

}
