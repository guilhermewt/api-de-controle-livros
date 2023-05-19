package com.biblioteca.requests;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

import com.biblioteca.enums.StatusBook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class BookPutRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id to identify the book to be updated")
	private Long id;
	
	@NotEmpty(message = "the book title cannot be empty")
	private String title;
	
	@NonNull
	@Schema(description =  "year the book was publication")
	private Date yearPublication;
	
	@Enumerated(EnumType.STRING)
	private StatusBook statusBook;
	
	public BookPutRequestBody(Long id, String title,Date yearPublication,StatusBook statusBook) {
		super();
		this.id = id;
		this.title = title;
		this.yearPublication = yearPublication;
		this.statusBook = statusBook;
	} 
	
	
}
