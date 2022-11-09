package com.biblioteca.requests;

import java.io.Serializable;
import java.util.Date;

import org.springframework.lang.NonNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class LoanPutRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id to identify the book to be updated")
	private Long id;
	
	@NonNull
	@Schema(description = "start of the loan")
	private Date startOfTheLoan;
	
	@NonNull
	@Schema(description = "end of the loan")
	private Date endOfLoan;

	public LoanPutRequestBody(Long id, Date startOfTheLoan, Date endOfLoan) {
		super();
		this.id = id;
		this.startOfTheLoan = startOfTheLoan;
		this.endOfLoan = endOfLoan;
	}

}
