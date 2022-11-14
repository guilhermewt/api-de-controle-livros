# Webservice de emprestimo de livros

sistema de emprestimo que faz o controler de livros, adicionando livros e fazendo emprestimos do mesmo.É uma Api publica onde cada usuário pode adicionar seus livros e quando necessário fazer o emprestimo desses livros.O sistema é construído em Restfull. Os testes são feitos com Junit, contendo teste de jpa para armazenamento de dados, teste unitarios com BDDMockito e teste de integracao tentando simula o maximo possível um ambiente real em produção com RestTemplate. A segurança é feita com spring securiry. Para documentar a Api foi usado a openAPI 3 com Swagger. O banco de dados para armazenamento foi o postgreeSQL

- para usar a api o usuario tem que está autenticado. Se for a primeira vez do usuario no sistema existe um endPoint para ele se cadastrar e assim poder usar o sistema

 <br>
  
# Tests
  - Tests de jpa
  - Tests unitários
  - Tests de integração com RestTemplate
  
  - Para rodar os testes Unitários na linha de comando basta adicionar -> mvn test
  - Para rodar os testes de Integração na linha de comando basta adicionar -> mvn test -Pintegration-tests
  - se o sistema estiver rodando sem banco de dados, com apenas o H2 então comente as anotaçoẽs (@SpringBootTest,@Test) da classe ProjetoBibliotecaApplicationTests
     
  
   
  
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



# Diagrama de classe do webservice de livros
<div>
	  <img src="https://github.com/guilhermewt/assets/blob/main/webservices%20de%20livros/diagrama%20de%20classe.png" style="background:#FFFFFF;
    		width: 500px;
      height:600px
    		padding: 5px;
    		margin: 5px;
    		float: left; ">
	</div>
 
 <br><br>


# Documentação swagger
<div>
	  <img src="https://github.com/guilhermewt/assets/blob/main/webservices%20de%20livros/userDomainResources.png" style="background:#FFFFFF;
    		width: 700px;
      height:800px
    		padding: 5px;
    		margin: 5px;
    		float: left; ">
	</div>
 
 <div>
	  <img src="https://github.com/guilhermewt/assets/blob/main/webservices%20de%20livros/loanResources.png" style="background:#FFFFFF;
    		width: 700px;
      height:800px
    		padding: 5px;
    		margin: 5px;
    		float: left; ">
	</div>
 <div>
	  <img src="https://github.com/guilhermewt/assets/blob/main/webservices%20de%20livros/bookResource.png" style="background:#FFFFFF;
    		width: 700px;
      height:800px
    		padding: 5px;
    		margin: 5px;
    		float: left; ">
	</div>

	

# Como executar o sistema?
Pré-requisitos: java 11

```bash
# clona repositorio
git clone git@github.com:guilhermewt/webservices_livros.git

# se não quiser instalar o banco de dados PostgreSQL basta mudar o arquivo 'application.properties' para test, nisso será usado o banco de dados para testes H2
- spring.profiles.active=test
- (usuario admin: rafasilva, senha: biblioteca), (usuario comum: marcospereira,senha:biblioteca)

# Para configurar o banco de dados que desejar, já existe um arquivo pre-configurado, basta colocar as informaçoẽs do seu banco como (url,username,password):
- application-dev.properties -> para desenvolver com banco de dados

# Entrar na pasta do projeto backEnd
cd webservices_livros

# executar o projeto na linha de comando
./mvn spring-boot:run


