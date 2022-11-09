package com.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.biblioteca.entities.Book;
import com.biblioteca.requests.BookPostRequestBody;
import com.biblioteca.requests.BookPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
	
	public static BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
	
	public abstract Book toBook(BookPostRequestBody bookPostRequestBody);
	                       
	public abstract Book toBook(BookPutRequestBody bookPutRequestBody);
	
}
