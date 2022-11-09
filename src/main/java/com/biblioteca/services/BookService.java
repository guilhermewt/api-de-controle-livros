package com.biblioteca.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Book;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.mapper.BookMapper;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.requests.BookPostRequestBody;
import com.biblioteca.requests.BookPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	private final UserDomainRepository userDomainRepository;
 
	private final GetUserDetails userAuthenticated;
	
	public List<Book> findAllNonPageable() {
		return bookRepository.findByUserDomainId(userAuthenticated.userAuthenticated().getId());
	}
	
	public Page<Book> findAll(Pageable pageable) {
		return bookRepository.findByUserDomainId(userAuthenticated.userAuthenticated().getId(), pageable);
	}

	public Book findByIdOrElseThrowResourceNotFoundException(long idBook) {
		return  bookRepository.findAuthenticatedUserBooksById(idBook, userAuthenticated.userAuthenticated().getId())
				.orElseThrow(() -> new BadRequestException("book not found"));
	}
	
	public List<Book> findByTitle(String title){
		return bookRepository.findAuthenticatedUserBooksByTitle(title,userAuthenticated.userAuthenticated().getId());
	}

	@Transactional
	public Book save(BookPostRequestBody bookPostRequestBody) {
		UserDomain userDomain = userDomainRepository.findById(userAuthenticated.userAuthenticated().getId()).get();
		
		Book book = BookMapper.INSTANCE.toBook(bookPostRequestBody);
		book.setUserDomain(userDomain);		
		return bookRepository.save(book);
	}
	
	@Transactional
	public void delete(long idBook) {
		try {
			bookRepository.deleteAuthenticatedUserBookById(findByIdOrElseThrowResourceNotFoundException(idBook)
					.getId(),userAuthenticated.userAuthenticated()
					.getId());
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	@Transactional
	public void update(BookPutRequestBody bookPutRequestBody) {
		UserDomain userDomain = userDomainRepository.findById(userAuthenticated.userAuthenticated().getId()).get();

		
		Book bookSaved = bookRepository.findAuthenticatedUserBooksById(bookPutRequestBody.getId(), 
				userAuthenticated.userAuthenticated()
				.getId())
				.orElseThrow(() -> new BadRequestException("book not found"));
		
		
		Book book = BookMapper.INSTANCE.toBook(bookPutRequestBody);
		book.setId(bookSaved.getId());
		book.setUserDomain(userDomain);	
		bookRepository.save(book);
	}
}
