package com.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.biblioteca.entities.Autor;
import com.biblioteca.requests.AutorPostRequestBody;
import com.biblioteca.requests.AutorPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class AutorMapper {
	
	public static AutorMapper INSTANCE = Mappers.getMapper(AutorMapper.class);
	
	public abstract Autor toAutor(AutorPostRequestBody autorPostRequestBody);
	
	public abstract Autor toAutor(AutorPutRequestBody autorPutRequestBody);
}
