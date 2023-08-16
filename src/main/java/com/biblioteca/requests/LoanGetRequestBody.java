package com.biblioteca.requests;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public class LoanGetRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@NotNull(message = "the loan startOfTheLoan cannot be empty")
	@Schema(description = "start of the loan")
	private Date startOfTheLoan;
	
	@NotNull(message = "the loan endOfLoan cannot be empty")
	@Schema(description = "end of the loan")
	private Date endOfLoan;
	
	@NotEmpty(message = "the loan addressee cannot be empty")
	private String addressee;

	public LoanGetRequestBody(Date startOfTheLoan, Date endOfLoan, String addressee) {
		super();
		this.startOfTheLoan = startOfTheLoan;
		this.endOfLoan = endOfLoan;
		this.addressee = addressee;
	}
	
}