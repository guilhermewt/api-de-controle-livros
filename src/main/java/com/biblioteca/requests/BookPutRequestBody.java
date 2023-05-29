package com.biblioteca.requests;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

import com.biblioteca.entities.Author;
import com.biblioteca.enums.StatusBook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class BookPutRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id to identify the book to be updated")
	private String id;
	
	@NotEmpty(message = "the book title cannot be empty")
	private String title;
	
	@NonNull
	@Schema(description =  "year the book was publication")
	private Date yearPublication;	
	private String description;
	private String imageLink;
	private StatusBook statusBook;
	
	@Builder.Default
	private Set<Author> authors = new HashSet<>();

	public BookPutRequestBody(String id, String title,Date yearPublication,String description, String imageLink,StatusBook statusBook) {
		super();
		this.id = id;
		this.title = title;
		this.yearPublication = yearPublication;
		this.description = description;
		this.imageLink = imageLink;
		this.statusBook = statusBook;
	} 
	
}
