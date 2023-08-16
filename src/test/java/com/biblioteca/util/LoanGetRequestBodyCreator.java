package com.biblioteca.util;

import com.biblioteca.requests.LoanGetRequestBody;

public class LoanGetRequestBodyCreator {
	
	public static LoanGetRequestBody createLoanGetRequestBodyCreator() {
		return LoanGetRequestBody.builder()
				.id(1l)
				.startOfTheLoan(DateConvert.convertData("2022/09/15"))
				.endOfLoan(DateConvert.convertData("2022/09/20"))
				.addressee("test addressee")
				.build();
	}
}
