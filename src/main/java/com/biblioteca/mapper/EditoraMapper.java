package com.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.biblioteca.entities.Editora;
import com.biblioteca.requests.EditoraPostRequestBody;
import com.biblioteca.requests.EditoraPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class EditoraMapper {
	
	public static EditoraMapper INSTANCE = Mappers.getMapper(EditoraMapper.class);
	
	public abstract Editora toEditora(EditoraPostRequestBody editoraPostRequestBody);
	
	public abstract Editora toEditora(EditoraPutRequestBody editoraPutRequestBody);
}
