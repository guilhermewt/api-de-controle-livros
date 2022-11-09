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
public class LoanPostRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NonNull
	@Schema(description = "start of the loan")
	private Date startOfTheLoan;
	
	@NonNull
	@Schema(description = "end of the loan")
	private Date endOfLoan;

	public LoanPostRequestBody(Date startOfTheLoan, Date endOfLoan) {
		super();
		this.startOfTheLoan = startOfTheLoan;
		this.endOfLoan = endOfLoan;
	}
	
}