package com.biblioteca.requests;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.biblioteca.entities.Genrer;
import com.biblioteca.enums.StatusBook;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class BookPostRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "the book title cannot be empty")
	private String title;
	private String description;
	private String imageLink;
	@NotNull(message = "the book staus cannot be empty or null")
    @Enumerated
	private StatusBook statusBook;
    private String authors;
    private String externalCode;
    
    private Set<Genrer> genrers;

    public BookPostRequestBody(String title,String description, String imageLink,StatusBook statusBook, String author,String externalCode, Set<Genrer> genrer) {
		super();
		this.title = title;
		this.statusBook = statusBook;
		this.authors = author; 
		this.externalCode = externalCode;
		this.genrers = genrer;
    }

}
