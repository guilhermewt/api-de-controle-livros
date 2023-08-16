package com.biblioteca.resources;

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
import com.biblioteca.requests.GenrerGetRequestBody;
import com.biblioteca.services.GenrerService;
import com.biblioteca.util.GenrerCreator;
import com.biblioteca.util.GenrerGetRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class GenrerResourceTest {
	
	@InjectMocks
	private GenrerResources genrerResource;

	@Mock
	private GenrerService genrerService;
	
	@BeforeEach
	void setUp() {
		BDDMockito.when(genrerService.findAllNonPageable()).thenReturn(GenrerCreator.createValidGenrer());	
	}

	@Test
	@DisplayName("find all genrers Return List Of genrers  whenSuccessful")
	void findAll_ReturnListOfGenrer_whenSuccessful() {
		List<GenrerGetRequestBody> genrer = this.genrerResource.findAllNonPageable().getBody();
		
		Assertions.assertThat(genrer).isNotNull().isNotEmpty();
		Assertions.assertThat(genrer.get(0).getId()).isNotNull();
		Assertions.assertThat(genrer.get(0)).isEqualTo(GenrerGetRequestBodyCreator.createGenrerGetRequestBodyCreator());
	}
	
	
}
