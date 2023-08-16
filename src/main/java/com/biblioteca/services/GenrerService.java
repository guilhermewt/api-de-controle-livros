package com.biblioteca.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biblioteca.entities.Genrer;
import com.biblioteca.repository.GenrerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenrerService {
	
	private final GenrerRepository genrerRepository;

	public List<Genrer> findAllNonPageable() {
		return genrerRepository.findAll();
	}
	
}
