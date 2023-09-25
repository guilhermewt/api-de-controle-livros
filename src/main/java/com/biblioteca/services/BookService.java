package com.biblioteca.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Book;
import com.biblioteca.entities.BooksStatistics;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.enums.StatusBook;
import com.biblioteca.mapper.BookMapper;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.repository.GenrerRepository;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.requests.BookPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.exceptions.ConflictRequestException;
import com.biblioteca.services.utilService.GetUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	private final UserDomainRepository userDomainRepository;

	private final GenrerRepository genrerRepository;

	private final GetUserDetails userAuthenticated;

	public BooksStatistics getBooksStatistics() {
		List<Book> books = bookRepository.findByUserDomainIdOrderByIdDesc(userAuthenticated.userAuthenticated().getId());
	
		Long numberOfBooks = books.stream().count();
		Long numberOfBooksToRead = books.stream().filter(x -> x.getStatusBook().equals(StatusBook.LER)).count();		
		Long numberOfBooksRead = books.stream().filter(x -> x.getStatusBook().equals(StatusBook.LIDO)).count();
		Long amountBooksReading = books.stream().filter(x -> x.getStatusBook().equals(StatusBook.LENDO)).count();
		Long numberBooksBorrowed = books.stream().filter(x -> x.getStatusBook().equals(StatusBook.EMPRESTADO)).count();
		
		return new BooksStatistics(numberOfBooks,numberOfBooksToRead,numberOfBooksRead,amountBooksReading,numberBooksBorrowed);
	}

	public List<Book> findAllNonPageable() {
		return bookRepository.findByUserDomainIdOrderByIdDesc(userAuthenticated.userAuthenticated().getId());
	}

	public Page<Book> findAll(Pageable pageable) {
//		Sort sort = Sort.by(Sort.Direction.DESC,"id");
//        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

		return bookRepository.findByUserDomainIdOrderByIdDesc(userAuthenticated.userAuthenticated().getId(), pageable);
	}

	public Page<Book> findAllBooksByStatus(StatusBook statusBook,Pageable pageable) {
		return bookRepository.findByUserDomainIdAndStatusBookOrderByIdDesc(userAuthenticated.userAuthenticated().getId(),statusBook,pageable);
	}	

	public Book findByIdOrElseThrowResourceNotFoundException(Long idBook) {
		return bookRepository.findByIdAndUserDomainId(idBook, userAuthenticated.userAuthenticated().getId())
				.orElseThrow(() -> new BadRequestException("book not found"));
	}

	public Page<Book> findByTitle(String title,Pageable pageable) {
		return bookRepository.findByUserDomainIdAndTitleContainingIgnoreCaseOrderByIdDesc(userAuthenticated.userAuthenticated().getId(),title,pageable);
	}

	public Page<Book> findByGenrer(String genrer,Pageable pageable) {
		return bookRepository.findByUserDomainIdAndGenrersNameContainingIgnoreCaseOrderByIdDesc(
				userAuthenticated.userAuthenticated().getId(), genrer,pageable);
	}

	public Page<Book> findByAuthors(String author,Pageable pageable) {
		return bookRepository.findByUserDomainIdAndAuthorsContainingIgnoreCaseOrderByIdDesc(
				userAuthenticated.userAuthenticated().getId(), author,pageable);
	}

	@Transactional
	public Book save(Book book) {
		if(book.getExternalCode() != null) {
			  if(book.getExternalCode().isEmpty()) {
		    	  book.setExternalCode(null);
		      }
		}
    
		UserDomain userDomain = userDomainRepository.findById(userAuthenticated.userAuthenticated().getId()).get();
		book.setUserDomain(userDomain);
		validationBook(book);

		return bookRepository.save(book);
	}

	public void validationBook(Book book) {

		List<Book> booksList = bookRepository.findByUserDomainId(userAuthenticated.userAuthenticated().getId());

		Boolean verifyBookExternal = booksList.stream()
				.filter((x) -> x.getExternalCode() != null )
				.anyMatch(x -> x.getExternalCode().equals(book.getExternalCode()) && x.getId() != book.getId());

		Boolean verifyBookEquals = booksList.stream().anyMatch(x -> x.equals(book) && x.getId() != book.getId());

		if (verifyBookExternal || verifyBookEquals) {
			throw new ConflictRequestException("the book already exist");
		}

		if (!genrerRepository.findAll().containsAll(book.getGenrers())) {
			throw new BadRequestException("Genrer not found");
		}

	}

	@Transactional
	public void delete(Long idBook) {
		try {
			bookRepository.deleteByIdAndUserDomainId(findByIdOrElseThrowResourceNotFoundException(idBook).getId(),
					userAuthenticated.userAuthenticated().getId());
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@Transactional
	public void update(BookPutRequestBody bookPutRequestBody) {
		UserDomain userDomain = userDomainRepository.findById(userAuthenticated.userAuthenticated().getId()).get();

		Book bookSaved = bookRepository
				.findByIdAndUserDomainId(bookPutRequestBody.getId(),
						userAuthenticated.userAuthenticated().getId())
				.orElseThrow(() -> new BadRequestException("book not found"));

		Book book = BookMapper.INSTANCE.toBook(bookPutRequestBody);
		book.setId(bookSaved.getId());
		book.setUserDomain(userDomain);
		validationBook(book);
		bookRepository.save(book);
	}
}
