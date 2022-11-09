package com.biblioteca.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_loan")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of= {"id"})
@SuperBuilder
@ToString
public class Loan implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "the loan startOfTheLoan cannot be empty")
	private Date startOfTheLoan;
	@NotNull(message = "the loan endOfLoan cannot be empty")
	private Date endOfLoan;
	
	@ManyToOne
	@JoinColumn(name = "user_domain_id")
	private UserDomain userDomain;
	
	@ManyToMany
	@JoinTable(name = "tb_loan_book", joinColumns = @JoinColumn(name = "loan_id")
                                    , inverseJoinColumns = @JoinColumn(name = "book_id")) 
	@Builder.Default
	private Set<Book> books = new HashSet<>();
	
	public Loan(Long id, Date startOfTheLoan, Date endOfLoan) {
		super();
		this.id = id;
		this.startOfTheLoan = startOfTheLoan;
		this.endOfLoan = endOfLoan;
	}
	
	@JsonIgnore
	public UserDomain getUserDomain() {
		return userDomain;
	}

}
