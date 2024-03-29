package com.biblioteca.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.biblioteca.entities.Loan;
import com.biblioteca.requests.LoanGetRequestBody;
import com.biblioteca.requests.LoanPostRequestBody;
import com.biblioteca.requests.LoanPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class LoanMapper {
	
	public static LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);
	
	public abstract Loan toLoan(LoanPostRequestBody loanPostRequestBody);
	
	public abstract Loan toLoan(LoanPutRequestBody loanPutRequestBody);
	
	public abstract Loan updateLoan(LoanPutRequestBody loanPutRequestBody,@MappingTarget Loan loanSaved);
	
	public abstract LoanGetRequestBody toLoanGetRequestBOdy(Loan loan);
	
	public abstract List<LoanGetRequestBody> toListOfLoanGetRequestBody(List<Loan> loan);


}
