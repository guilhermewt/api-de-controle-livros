package com.biblioteca.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Loan;
import com.biblioteca.entities.Book;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.enums.StatusBook;
import com.biblioteca.mapper.LoanMapper;
import com.biblioteca.repository.LoanRepository;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.requests.LoanPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {
	
	private final LoanRepository loanRepository;

	private final UserDomainRepository userDomainRepository;

	private final BookRepository bookRepository;
	
	private final GetUserDetails userAuthenticated;

	public List<Loan> findAllNonPageable() {
		return loanRepository.findByUserDomainId(userAuthenticated.userAuthenticated().getId());
	}
	
	public Page<Loan> findAll(Pageable pageable) {
		return loanRepository.findByUserDomainId(userAuthenticated.userAuthenticated().getId(), pageable);
	}

	public Loan findByIdOrElseThrowResourceNotFoundException(long id) {
		return loanRepository.findAuthenticatedUserById(id, userAuthenticated.userAuthenticated().getId()).orElseThrow(() -> new BadRequestException("loan not found"));	
	}

	@Transactional
	public Loan save(Loan loan, Long idbook) {
		Book bookSaved = bookRepository.findByIdAndUserDomainId(idbook, userAuthenticated.userAuthenticated().getId())
				.orElseThrow(() -> new BadRequestException("book not found"));
		
		if(bookSaved.getloans() != null) {
			throw new BadRequestException("this book is already on loan");
		}
		
		UserDomain usuarioSaved = userDomainRepository.findById(userAuthenticated.userAuthenticated().getId()).get();

		bookSaved.setStatusBook(StatusBook.EMPRESTADO);
		loan.setUserDomain(usuarioSaved);
		loan.setBooks(bookSaved);	
		
		return loanRepository.save(loan);
	}

	public void delete(long idBook) {
		try {
			loanRepository.deleteAuthenticatedUserLoanById(findByIdOrElseThrowResourceNotFoundException(idBook).getId(),userAuthenticated.userAuthenticated().getId());
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@Transactional
	public void update(LoanPutRequestBody loansPutRequestBody) {
		Loan loan = loanRepository
				.findByIdAndUserDomainId(loansPutRequestBody.getId(), userAuthenticated.userAuthenticated().getId())
				.orElseThrow(() -> new BadRequestException("loan not found"));
		
//		 Book book = loan.getBooks();
//		    book.getGenrers().size();
		
		LoanMapper.INSTANCE.updateLoan(loansPutRequestBody, loan);
				
		loanRepository.save(loan);
	}
	
}
