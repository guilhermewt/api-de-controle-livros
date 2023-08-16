package com.biblioteca.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.biblioteca.entities.Genrer;
import com.biblioteca.requests.GenrerGetRequestBody;

@Mapper(componentModel = "spring")
public abstract class GenrerMapper {
	
	public static GenrerMapper INSTANCE = Mappers.getMapper(GenrerMapper.class);
	
	public abstract List<GenrerGetRequestBody> toListOfGenrerGetRequetBody(List<Genrer> genrer);
	
}
