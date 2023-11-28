<div align="center">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/spring-boot.webp" style="width:90px;height:50px;">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/java.webp" style="width:70px;height:50px;">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/jwt.png" style="width:90px;height:50px;">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/postgre.jpg" style="width:80px;height:50px;">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/docker.jpg" style="width:90px;height:50px;">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/JUnit.svg" style="width:90px;height:50px;">
</div>



# API GESTÃO DE LIVROS
 Este projeto consiste em um sistema de gerenciamento de livros que permite o controle e organização da biblioteca pessoal de um usuário. O sistema possui as seguintes funcionalidades:

1. Adição de livros: Os usuários podem adicionar livros à sua biblioteca, fornecendo informações como título, autor, gênero, ano de publicação.

2. Acompanhamento do status de leitura: O sistema permite que os usuários rastreiem o status de leitura de cada livro. Eles podem indicar se já leram o livro, se estão lendo no momento ou se ainda não começaram a leitura.

3. Estatísticas de leitura: O sistema gera estatísticas sobre os livros lidos pelos usuários. Ele calcula o número total de livros lidos, o número de livros que ainda não foram lidos e o número de livros que estão sendo lidos no momento.

4. Funcionalidade de empréstimo: Os usuários têm a opção de marcar livros como emprestados, indicando o nome da pessoa para quem o livro foi emprestado e a data prevista para a devolução. Isso ajuda a manter o controle dos livros emprestados e a gerenciar os prazos de devolução.

5. Consulta de livros: O sistema permite que os usuários realizem consultas na biblioteca. Eles podem pesquisar livros por nome, gênero ou status de leitura (lidos, não lidos, lendo). Isso facilita a localização de livros específicos ou a visualização de uma lista de livros com determinadas características.


## Guia

- [Tecnologias utilizadas](#Tecnologias-utilizadas)
- [Arquitetura](#Arquitetura)
- [Como usar a Api](#Como-usar-a-Api)
- [Como executar o sistema](#Como-executar-o-sistema)

# Tecnologias utilizadas
  - Java
  - spring boot
  - maven
  - jpa/hibernate
  - postgreSQL
  - JPQL
  - swagger
  - paginação
  - Docker

# Security
  - spring security
  - auth
  - JWT
  
# Tests
  - Tests de jpa
  - Tests unitários
  - Tests de integração com RestTemplate
  
  - Para rodar os testes Unitários na linha de comando basta adicionar -> mvn test
  - Para rodar os testes de Integração na linha de comando basta adicionar -> mvn test -Pintegration-tests
  - se o sistema estiver rodando sem banco de dados, com apenas o H2 então comente as anotaçoẽs (@SpringBootTest,@Test) da classe ProjetoBibliotecaApplicationTests
   
# Arquitetura
<div>
	  <img src="https://github.com/guilhermewt/assets/blob/main/webservices%20de%20livros/diagrama%20de%20classe.png" style="background:#FFFFFF;
    		width: 500px;
      height:600px
    		padding: 5px;
    		margin: 5px;
    		float: left; ">
	</div>
 
 <br><br>


# Como usar a Api

  - online:https://api-spring-book.onrender.com/
  - localmente: localhost:8080

### 1 - Criar usuário:Faça um cadastro utilizando a rota
     https://api-spring-book.onrender.com/swagger-ui/index.html#/user-domain-resources/saveUser.        
  - Envie os dados necessários para criar um novo usuário, como nome de usuário, email, ,username e senha.

2 - Fazer o login:

- Para obter um token de autenticação, faça o login na rota '/login'.
- Envie o nome de usuário (username) e senha para autenticar e receber o token JWT.
- se estiver usando swagger basta pegar o token na rotar login e colocar em autorize que podera usar toda api pelo swagger.

### 3 - Listar os livros: Liste os livros no endpoint 
     https://api-spring-book.onrender.com/swagger-ui/index.html#/book-resources/findAllNonPageable
 
- lembrando que existe outros endpoint com paginação

### 4 - salvar um livro:
     https://api-spring-book.onrender.com/swagger-ui/index.html#/book-resources/save_1
- alguns campos como titulo,descricao,autor, genero etc... Serão necessários

- Lembre-se de incluir o token JWT nos cabeçalhos das requisições subsequentes após fazer o login. O token de autenticação será retornado após o login e deve ser enviado no cabeçalho Authorization de todas as solicitações subsequentes.


	

# Como executar o sistema
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


