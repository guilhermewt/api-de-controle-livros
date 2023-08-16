package com.biblioteca.mapper;

import java.util.List;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.biblioteca.entities.UserDomain;
import com.biblioteca.requests.UserDomainGetRequestBody;
import com.biblioteca.requests.UserDomainPostRequestBody;
import com.biblioteca.requests.UserDomainPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class UserDomainMapper {
	
	public static final UserDomainMapper INSTANCE = Mappers.getMapper(UserDomainMapper.class);
	
	public abstract UserDomain toUserDomain(UserDomainPostRequestBody userDomainPostRequestBody);
	                             
	public abstract UserDomain toUserDomain(UserDomainPutRequestBody userDomainPutRequestBody);
	
	public abstract UserDomain updateUserDomain(UserDomainPutRequestBody userDomainPutRequestBody, @MappingTarget UserDomain userDomain);
	
	public abstract UserDomainGetRequestBody toUserDomainGetRequestBody(UserDomain userDomain);
	
	public abstract List<UserDomainGetRequestBody> toListOfUserDomainGetRequestBody(List<UserDomain> userDomain);
}
