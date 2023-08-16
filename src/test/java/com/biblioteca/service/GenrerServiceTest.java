package com.biblioteca.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.biblioteca.entities.Genrer;
import com.biblioteca.repository.GenrerRepository;
import com.biblioteca.services.GenrerService;
import com.biblioteca.util.GenrerCreator;

@ExtendWith(SpringExtension.class)
public class GenrerServiceTest {
	
	@InjectMocks
	private GenrerService genrerService;

	@Mock
	private GenrerRepository genrerRepository;
	
	@BeforeEach
	void setUp() {
		BDDMockito.when(genrerRepository.findAll()).thenReturn(GenrerCreator.createValidGenrer());	
	}

	@Test
	@DisplayName("find all genrer Return List Of genrer whenSuccessful")
	void findAll_ReturnListOfGenrer_whenSuccessful() {
		List<Genrer> genrer = this.genrerService.findAllNonPageable();
		
		Assertions.assertThat(genrer).isNotNull().isNotEmpty();
		Assertions.assertThat(genrer.get(0).getId()).isNotNull();
		Assertions.assertThat(genrer.get(0)).isEqualTo(GenrerCreator.createValidGenrer().get(0));
	}
	
	
}
