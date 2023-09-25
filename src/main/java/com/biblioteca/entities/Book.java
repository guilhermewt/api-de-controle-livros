package com.biblioteca.entities;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.biblioteca.enums.StatusBook;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_book")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of={"title","authors","userDomain"})
@SuperBuilder
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "the book title cannot be empty")
	private String title;
	private String description;
	private String imageLink;
	private StatusBook statusBook;
	private String authors;
	private String externalCode;
	
	@ManyToOne
	@JoinColumn(name = "userDomain_id")
	private UserDomain userDomain;

	@OneToOne(mappedBy = "books",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private Loan loans;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_book_genrer", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genrer_id"))
	@Builder.Default
	private Set<Genrer> genrers = new HashSet<>();
	
	public Book(Long id, String title,String description, String imageLink,StatusBook statusBook, String author,String externalCode,Set<Genrer> genrers) {
		super();
		this.id = id;
		this.title = title;
		this.statusBook = statusBook;
		this.authors = author;
		this.externalCode = externalCode;
		this.genrers = genrers;
    }

	
	public UserDomain getUserDomain() {
		return userDomain;
	}

	@JsonIgnore
	public Loan getloans() {
		return loans;
	}

}
