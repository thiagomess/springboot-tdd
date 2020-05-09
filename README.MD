[![Build Status](https://travis-ci.com/thiagomess/springboot-tdd.svg?branch=master)](https://travis-ci.com/thiagomess/springboot-tdd)

# API RESTfull com Spring Boot utilizando TDD, CI e CD
Certificado: https://www.udemy.com/certificate/UC-980f64fa-4c50-4275-a7c9-d1a44bf262f2/

## Detalhes do projeto
*Esse projeto possui as seguintes características:*

* Projeto criado com Spring Boot e Java 11
* Banco de dados Postgres com JPA e Spring Data JPA
* Versionamento de banco de dados com Flyway
* Testes com JUnit e Mockito com banco H2 em memória
* Caching com EhCache
* Nuvem do Heroku
* Integração contínua com TravisCI
* Project Lombok
* Documentação dos endpoints com Swagger
* Segurança da API com autenticação via tokens JWT 

## Como executar a aplicação
Certifique-se de ter o Maven instalado e adicionado ao PATH de seu sistema operacional, assim como o Git, crie um banco de dados no postgres e altere o arquivo application.properties informando as credenciais para a aplicação acessar a base de dados, não se preocupe com a criação das tabelas, o flyway se encarregará dessa função.
```
git clone https://github.com/thiagomess/springboot-tdd.git
mvn spring-boot:run
Acesse os endpoints através da url http://localhost:8080
```

### Como executar os testes
Os testes podem ser executados com o seguinte comando:

```
mvn test
```
### Documentação
Utilize a interface do Swagger para ter acesso a documentação dos endpoints, ela está disponível na url http://localhost:8080/swagger-ui.html

### Heroku
A aplicação está disponível no heroku através da url https://api-wallet-tdd.herokuapp.com/ e documentação https://api-wallet-tdd.herokuapp.com/swagger-ui.html

O login deve ser feito na url: https://api-wallet-tdd.herokuapp.com/auth, com o payload:

```
{
	"email":"admin@teste.com",
	"password":"123456"
}

```
E inserido o token no Authorize:
Exemplo:

```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0ZS5jb20iLCJyb2xlIjoiUk9MRV9BRE1JTiIsImNyZWF0ZWQiOjE1ODg5ODQ3NTAzMDQsImV4cCI6MTU4ODk4NTM1MH0.05xwVDD0V7GVJTM2ZTrs-7tJWBuMeMjpp9s0HNj45OKBXViY7Q5nJ96QTJ3byBfJPdUyI2ZJoKjC6Bd63FKUcg
```

