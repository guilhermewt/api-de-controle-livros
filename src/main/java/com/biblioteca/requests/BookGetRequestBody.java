package com.biblioteca.requests;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.biblioteca.entities.Genrer;
import com.biblioteca.enums.StatusBook;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class BookGetRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@NotEmpty(message = "the book title cannot be empty")
	private String title;
	private String description;
	private String imageLink;
	private StatusBook statusBook;
    private String authors;
    private String externalCode;
    
    private Set<Genrer> genrers;

    public BookGetRequestBody(Long id,String title,String description, String imageLink,StatusBook statusBook, String author,String externalCode, Set<Genrer> genrer) {
		super();
		this.id = id;
		this.title = title;
		this.statusBook = statusBook;
		this.authors = author; 
		this.externalCode = externalCode;
		this.genrers = genrer;
    }

}
