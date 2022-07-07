package com.biblioteca.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_livro")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of={"id","titulo"})
@ToString
public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private Date anoPublicacao;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToMany(mappedBy = "livros")
	private Set<Emprestimo> emprestimos = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "editora_id")
	private Editora editora;

	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Autor autor;
	
	public Livro(Long id, String titulo, Date anoPublicacao) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.anoPublicacao = anoPublicacao;
	}

	@JsonIgnore
	public Usuario getUsuario() {
		return usuario;
	}

	@JsonIgnore
	public Set<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

}
