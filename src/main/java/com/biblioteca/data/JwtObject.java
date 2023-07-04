package com.biblioteca.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class JwtObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String token;
	
}