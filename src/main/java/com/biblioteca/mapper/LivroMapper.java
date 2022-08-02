package com.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.biblioteca.entities.Livro;
import com.biblioteca.requests.LivroPostRequestBody;
import com.biblioteca.requests.LivroPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {
	
	public static LivroMapper INSTANCE = Mappers.getMapper(LivroMapper.class);
	
	public abstract Livro toLivro(LivroPostRequestBody livroPostRequestBody);
	
	public abstract Livro toLivro(LivroPutRequestBody livroPutRequestBody);
	
}
