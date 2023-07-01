

### projeto Em construção <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0fm1v4G3YfyGZ5fOEJtQjYIDM3D_kU5ld8w&usqp=CAU">

# Webservice de emprestimo de livros
 Este projeto consiste em um sistema de gerenciamento de livros que permite o controle e organização eficientes da biblioteca pessoal de um usuário. O sistema possui as seguintes funcionalidades:

1. Adição de livros: Os usuários podem adicionar livros à sua biblioteca, fornecendo informações como título, autor, gênero, ano de publicação e qualquer outra informação relevante.

2. Acompanhamento do status de leitura: O sistema permite que os usuários rastreiem o status de leitura de cada livro. Eles podem indicar se já leram o livro, se estão lendo no momento ou se ainda não começaram a leitura.

3. Estatísticas de leitura: O sistema gera estatísticas sobre os livros lidos pelos usuários. Ele calcula o número total de livros lidos, o número de livros que ainda não foram lidos e o número de livros que estão sendo lidos no momento.

4. Funcionalidade de empréstimo: Os usuários têm a opção de marcar livros como emprestados, indicando o nome da pessoa para quem o livro foi emprestado e a data prevista para a devolução. Isso ajuda a manter o controle dos livros emprestados e a gerenciar os prazos de devolução.

5. Consulta de livros: O sistema permite que os usuários realizem consultas na biblioteca. Eles podem pesquisar livros por nome, gênero ou status de leitura (lidos, não lidos, lendo). Isso facilita a localização de livros específicos ou a visualização de uma lista de livros com determinadas características.

Essas funcionalidades fornecem aos usuários um sistema completo para gerenciar sua biblioteca pessoal de forma organizada. Eles podem adicionar novos livros, acompanhar seu progresso de leitura, estabelecer empréstimos e consultar facilmente sua coleção com base em diferentes critérios. Isso permite que os usuários tenham um controle maior sobre sua leitura e promove uma experiência aprimorada de gerenciamento de livros.

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


