package com.biblioteca.util;

import com.biblioteca.entities.Loan;

public class LoanCreator {
	
	public static Loan createValidLoan() {
		return Loan.builder()
				.id(1l)
				.startOfTheLoan(DateConvert.convertData("2022/09/15"))
				.endOfLoan(DateConvert.convertData("2022/09/20"))
				.userDomain(UserDomainCreator.createUserDomainWithRoleADMIN())
				.addressee("test addressee")
				.build();
	}
	
	public static Loan createLoanToBeSaved() {
		return Loan.builder()
				.startOfTheLoan(DateConvert.convertData("2022/09/15"))
				.endOfLoan(DateConvert.convertData("2022/09/20"))
				.addressee("test addressee")
				.build();
	}
	
	public static Loan createUpdatedLoan() {
		return Loan.builder()
				.id(1l)
				.startOfTheLoan(DateConvert.convertData("2024/04/14"))
				.endOfLoan(DateConvert.convertData("2024/04/24"))
				.addressee("test addressee")
				.build();
	}
}
