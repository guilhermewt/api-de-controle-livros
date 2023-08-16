package com.biblioteca.util;

import com.biblioteca.requests.LoanPostRequestBody;

public class LoanPostRequestBodyCreator {
	
	public static LoanPostRequestBody createLoanPostRequestBodyCreator() {
		return LoanPostRequestBody.builder()
				.startOfTheLoan(DateConvert.convertData("2022/09/15"))
				.endOfLoan(DateConvert.convertData("2022/09/20"))
				.addressee("test addressee")
				.build();
	}
}
