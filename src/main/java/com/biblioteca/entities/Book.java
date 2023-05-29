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
@EqualsAndHashCode(of={"id","title"})
@SuperBuilder
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@NotEmpty(message = "the book title cannot be empty")
	private String title;	
	private Date yearPublication; 	
	private String description;
	private String imageLink;
	
	@ManyToMany(mappedBy = "books")
	@Builder.Default
	private Set<Author> authors = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "userDomain_id")
	private UserDomain userDomain;

	@ManyToMany(mappedBy = "books")
	@Builder.Default
	private Set<Loan> loans = new HashSet<>();
	
	private StatusBook statusBook;
	
	public Book(String id, String title, Date yearPublication,String description, String imageLink,StatusBook statusBook) {
		super();
		this.id = id;
		this.title = title;
		this.yearPublication = yearPublication;
		this.statusBook = statusBook;
	}

	@JsonIgnore
	public UserDomain getUserDomain() {
		return userDomain;
	}

	@JsonIgnore
	public Set<Loan> getloans() {
		return loans;
	}

}
