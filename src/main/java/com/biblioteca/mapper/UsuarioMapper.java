package com.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.biblioteca.entities.Usuario;
import com.biblioteca.requests.UsuarioPostRequestBody;
import com.biblioteca.requests.UsuarioPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {
	
	public static final UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
	
	public abstract Usuario toUsuario(UsuarioPostRequestBody usuarioPostRequestBody);
	
	public abstract Usuario toUsuario(UsuarioPutRequestBody usuarioPutRequestBody);
	
	public abstract Usuario updateUser(UsuarioPutRequestBody usuarioPutRequestBody, @MappingTarget Usuario usuario);
}
