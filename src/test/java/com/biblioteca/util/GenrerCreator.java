package com.biblioteca.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.biblioteca.entities.Genrer;

public class GenrerCreator {
	
	public static List<Genrer> createValidGenrer() {
		return List.of(new Genrer(1l,"Ficção científica"));
	}
	
public static Set<Genrer> createValidGenrerSet() {
		
		Set<Genrer> genrer = new HashSet<>();
				genrer.add(new Genrer(1l,"Ficção científica"));
		
		return genrer;
	}
	
}
