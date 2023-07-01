package com.biblioteca.requests;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

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
	private StatusBook statusBook;
    private String authors;
    private String externalCode;
    
    private List<Genrer> genrers;

    public BookPostRequestBody(String title,String description, String imageLink,StatusBook statusBook, String author,String externalCode, List<Genrer> genrer) {
		super();
		this.title = title;
		this.statusBook = statusBook;
		this.authors = author; 
		this.externalCode = externalCode;
		this.genrers = genrer;
    }

}
