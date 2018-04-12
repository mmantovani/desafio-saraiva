# Instruções para execução

O projeto foi desenvolvido utilizando o framework Spring Boot.

Ao ser executada pela primeira vez, a aplicação irá criar as tabelas de banco de dados automaticamente. Para isso está sendo usado o Liquibase. Porém, antes de rodar a aplicação é preciso criar a base de dados e o usuário que será usado para conexão.

A aplicação utiliza os seguintes valores default para conexão:

```sh
spring.datasource.url=jdbc:mysql://localhost:3306/saraiva
spring.datasource.username=saraiva
spring.datasource.password=saraiva
```

Por padrão, o serviço REST estará respondendo na porta:

```sh
server.port=8080
```
Para executar a aplicação com os valores default:

```sh
$ mvn spring-boot:run
```

Esses parâmetros podem ser alterados na linha de comando, conforme exemplo abaixo:

```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9000,--spring.datasource.password=xx"
```

Também se pode alterar o seguinte arquivo de propriedades:

```sh
src/main/resources/application.properties
```

# Cobertura

Use o seguinte comando para executar os testes automatizados e gerar o relatório de cobertura:

```sh
mvn clean verify
```

O relatório será gerado no diretório `target/site/jacoco`.

Para execução dos testes, é utilizada uma base de dados em memória H2, não interferindo com a base de dados real.

