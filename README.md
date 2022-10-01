# webservice de uma biblioteca

sistema de uma biblioteca que faz o controler de livros de uma livraria, adicionando livros e fazendo emprestimos do mesmo e associando-os as suas respectivas
editoras. O sistema é construído em Restfull. Os testes são feitos com Junit, contendo teste de de jpa para armazenamento de dados, teste unitarios com BDDMockito e teste de integracao tentando simula o maximo possível um ambiente real com RestTemplate. A segurança é feita com spring securiry. Para a documentar a Api foi usado a openAPI 3 com Swagger. O banco de dados para armazenamento foi o postgreeSQL


Webservice de um projeto que contem as funcionalidades de fazer pedidos, escolher produtos, o sistema pega estes pedidos e retornar as
características como preço,preço tota dependendo da quantidade de produtos feitos e suas respectivas categorias. Faz a verificação se o 
pagamento do pedido já foi efetuado ou em que etapa está (pago,aguardando pagamento...).  Um webService que trabalha com a venda de produtos  
de uma loja virtual
  
  <br>
  
  Algumas categorias e seus produtos
  
  <br>
  Categoria: eletronics
  <br>
    * smart TV
    * geladeira
    * fogao
 
 <br>
 <br>
   Categoria: Books
  <br>
    * The lord of the rings
    * 50 tons de sinza
    * A cabana
    
  <br>  
  <br>
  Categoria: Computers
  <br>
    * MACBOOK PRO
    * PC GAMES
    * IPHONE 13
  
  
# tests
  * TEST DE JPA
  * TESTS UNITÁRIOS
  * TESTS DE INTEGRACAO COM RestTemplate
  
# security
  * spring security
  * auth
  
# tecnologias utilizadas
  spring boot<p>
  maven<p>
  jpa/hibernate<p>
  postgreSQL
  
## modelo conceitual

# diagrama de classe da biblioteca
<img src="https://github.com/guilhermewt/assets/blob/main/IMAGE%20-%20diagrama%20de%20classe%20da%20biblioteca.png">


# diagrama de de memoria da biblioteca
<img src="https://github.com/guilhermewt/assets/blob/main/IMAGE-%20diagrama%20de%20memoria.png">

