package com.biblioteca.resources;

import java.util.List;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.Book;
import com.biblioteca.enums.StatusBook;
import com.biblioteca.requests.BookPostRequestBody;
import com.biblioteca.requests.BookPutRequestBody;
import com.biblioteca.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookResources {

	private final BookService serviceBook;

	@GetMapping(value = "/all")
	@Operation(summary = "find all books non paginated")
	public ResponseEntity<List<Book>> findAllNonPageable(){
		return ResponseEntity.ok(serviceBook.findAllNonPageable());
	}
	
	@GetMapping
	@Operation(summary = "find all books paginated", description = "the default size is 20, use the parameter to change the default value")
	public ResponseEntity<Page<Book>> findAll(@ParameterObject Pageable pageable){
		return ResponseEntity.ok(serviceBook.findAll(pageable));
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "find book by id")
	public ResponseEntity<Book> findById(@PathVariable Long id){
		return ResponseEntity.ok(serviceBook.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	@GetMapping(value = "/findbytitle")
	@Operation(summary = "find book by title")
	public ResponseEntity<List<Book>> findByTitle(@RequestParam String title){
		return ResponseEntity.ok(serviceBook.findByTitle(title));
	}
	
	@GetMapping(value = "/findbookByStatus")
	@Operation(summary = "find book by status")
	public ResponseEntity<List<Book>> findBookByStatus(@RequestParam String statusBook){
		return ResponseEntity.ok(serviceBook.findAllBooksByStatusNonPageable(StatusBook.valueOf(statusBook)));
	}
	
	@GetMapping(value = "/find-by-author")
	@Operation(summary = "find book by title")
	public ResponseEntity<List<Book>> findByAuthor(@RequestParam String author){
		return ResponseEntity.ok(serviceBook.findByAuthors(author));
	}
	
	@GetMapping(value = "/find-by-genrer")
	@Operation(summary = "find book by genrer")
	public ResponseEntity<List<Book>> findByGenrer(@RequestParam String genrer){
		return ResponseEntity.ok(serviceBook.findByGenrer(genrer));
	}
	
	@PostMapping
	@Operation(description = "for the book to be made,the publisher Id and the author Id are required")
	public ResponseEntity<Book> save(@RequestBody @Valid BookPostRequestBody bookPostRequestBody){
		return new ResponseEntity<Book>(serviceBook.save(bookPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when publisher does not exist in the dataBase")
	})
	public ResponseEntity<Void> delete(@PathVariable Long id){
		serviceBook.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
    @PutMapping
    @Operation(description = "for the book to be made, the user Id,the publisher Id and the author Id are required")
	public ResponseEntity<Void> update(@RequestBody @Valid BookPutRequestBody bookPutRequestBody){
		serviceBook.update(bookPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
