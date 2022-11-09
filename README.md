# Webservice de emprestimo de livros

sistema de emprestimo que faz o controler de livros, adicionando livros e fazendo emprestimos do mesmo.É uma api publica onde cada usuário pode adicionar seus livros e quando necessário fazer o emprestimo desses livros.O sistema é construído em Restfull. Os testes são feitos com Junit, contendo teste de jpa para armazenamento de dados, teste unitarios com BDDMockito e teste de integracao tentando simula o maximo possível um ambiente real com RestTemplate. A segurança é feita com spring securiry. Para documentar a Api foi usado a openAPI 3 com Swagger. O banco de dados para armazenamento foi o postgreeSQL

- para usar a api o usuario tem que está autenticado. Se for a primeira vez do usuario no sistema existe um endPoint para ele se cadastrar e assim poder usar o sistema

 <br>
  
# Tests
  - Tests de jpa
  - Tests unitários
  - Tests de integração com RestTemplate
  
  - Para rodar os testes Unitários na linha de comando basta adicionar -> mvn test
  - Para rodar os testes de Integração na linha de comando basta adicionar -> mvn test -Pintegration-tests
  
# Security
  - spring security
  - auth
  
# Tecnologias utilizadas
  - spring boot<p>
  - maven<p>
  - jpa/hibernate<p>
  - postgreSQL
  - JPQL
  - swagger
  
## Modelo conceitual

# Diagrama de classe da biblioteca
<img src="https://github.com/guilhermewt/assets/blob/main/IMAGE%20-%20diagrama%20de%20classe%20da%20biblioteca.png">


# Diagrama de de memoria da biblioteca
<img src="https://github.com/guilhermewt/assets/blob/main/IMAGE-%20diagrama%20de%20memoria.png">

