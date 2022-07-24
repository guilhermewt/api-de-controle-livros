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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_emprestimo")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of= {"id"})
@SuperBuilder
public class Emprestimo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date dataEmprestimo;
	private Date dataDevolucao;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@ManyToMany
	@JoinTable(name = "tb_emprestimo_livro", joinColumns = @JoinColumn(name = "emprestimo_id")
                                                           , inverseJoinColumns = @JoinColumn(name = "livro_id"))
    
	private Set<Livro> livros = new HashSet<>();
	
	public Emprestimo(Long id, Date dataEmprestimo, Date dataDevolucao) {
		super();
		this.id = id;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
	}
	
	@JsonIgnore
	public Usuario getUsuario() {
		return usuario;
	}

}
