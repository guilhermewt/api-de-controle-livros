package com.biblioteca.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class swaggerConfiguration {
	
	@Bean
	public OpenAPI springShopOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("Gestão de Livros - API")
	              .description(description())
	              .contact(contact())
	              .license(new License().name("Apache License Version 2.0").url("http://springdoc.org")))
	           
	              .externalDocs(new ExternalDocumentation()
	              .description("Github project Documentation")
	              .url("https://github.com/weguilherme/api-de-controle-livros"))
	              .components(new Components().addSecuritySchemes("bearer-key", 
	            		  new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	  }
	
	public Contact contact() {
		return new Contact()
				.name("Guilherme Silva")
				.url("https://github.com/weguilherme")
				.email("dguilherme946@gmail.com");
	}
	
	public String  description() {
		return "Este projeto é um sistema de gerenciamento de livros que permite aos usuários controlar sua biblioteca, acompanhar o status de leitura e fazer consultas com facilidade.";
	}
	
}

