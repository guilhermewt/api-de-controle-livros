package com.biblioteca.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.biblioteca.entities.Genrer;
import com.biblioteca.util.GenrerCreator;


@DataJpaTest
@DisplayName("test for genrer repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class GenrerRepositoryTest {

	@Autowired
	private GenrerRepository genrerRepository;
	
	@BeforeEach
	public void setUp() {
		this.genrerRepository.saveAll(GenrerCreator.createValidGenrer());
	}
	
	@Test
	@DisplayName("find all genrer return list of genrer  whensuccessful")
	void findAll_returnListOfGenrer_whenSuccessful() {
		List<Genrer> genrerSaved = this.genrerRepository.findAll();
		
		Assertions.assertThat(genrerSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(genrerSaved.get(0)).isEqualTo(GenrerCreator.createValidGenrer().get(0));
	}
	
}
