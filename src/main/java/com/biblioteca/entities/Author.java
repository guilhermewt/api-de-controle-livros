package com.biblioteca.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_author")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of={"id","name"})
@SuperBuilder
public class Author implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToMany
	@Builder.Default
	@JoinTable(name = "tb_author_book", joinColumns = @JoinColumn(name = "tb_author_id"),
													inverseJoinColumns = @JoinColumn(name = "tb_book_id"))
	private Set<Book> books = new HashSet<>();
}
