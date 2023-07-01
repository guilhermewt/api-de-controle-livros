package com.biblioteca.util;

import java.util.List;

import com.biblioteca.entities.Genrer;

public class GenrerCreator {
	
	public static List<Genrer> createValidGenrer() {
		
		return List.of(Genrer.builder()
				.id(1l)
				.name("Ficção científica")
				.build());
	}
	
}
