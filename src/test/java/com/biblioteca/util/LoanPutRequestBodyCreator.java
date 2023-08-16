package com.biblioteca.util;

import com.biblioteca.requests.LoanPutRequestBody;

public class LoanPutRequestBodyCreator {
	
	public static LoanPutRequestBody createLoanPutRequestBodyCreator() {
		return LoanPutRequestBody.builder()
				.id(1l)
				.startOfTheLoan(DateConvert.convertData("2024/04/14"))
				.endOfLoan(DateConvert.convertData("2024/04/24"))
				.addressee("test addressee")
				.build();
	}
}
