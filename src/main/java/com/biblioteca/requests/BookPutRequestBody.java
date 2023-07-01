package com.biblioteca.requests;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.biblioteca.entities.Genrer;
import com.biblioteca.enums.StatusBook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.asm.Advice.This;

@NoArgsConstructor
@Data
@SuperBuilder
public class BookPutRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id to identify the book to be updated")
	private Long id;
	
	@NotEmpty(message = "the book title cannot be empty")
	private String title;
	
	private String description;
	private String imageLink;
	private StatusBook statusBook;
    private String authors;
    private String externalCode;
    
    private List<Genrer> genrers;

	public BookPutRequestBody(Long id, String title,String description, String imageLink,StatusBook statusBook, String authors,String externalCode, List<Genrer> genrer) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.imageLink = imageLink;
		this.statusBook = statusBook;
		this.authors = authors;
		this.externalCode = externalCode;
		this.genrers = genrer;
	} 
	
}
