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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_livro")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of={"id","titulo"})
@SuperBuilder
public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "the livro titulo cannot be empty")
	private String titulo;
	@NotNull(message = "the livro ano publicacao cannot be null")
	private Date anoPublicacao; 

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToMany(mappedBy = "livros")
	@Builder.Default
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
		this.titulo = titulo;;
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
