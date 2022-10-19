package com.biblioteca.requests;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class AutorPutRequestBody {
	
    @Schema(description = "id to identify the book to be updated")
	private Long id;
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@ManyToMany
	@JoinTable(name = "tb_emprestimo_livro", joinColumns = @JoinColumn(name = "emprestimo_id")
                                                           , inverseJoinColumns = @JoinColumn(name = "livro_id"))
    
	@Builder.Default
	private Set<Livro> livros = new HashSet<>();
	
	public AutorPutRequestBody(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
}
