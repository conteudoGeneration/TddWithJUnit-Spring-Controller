# Spring TDD com JUnit  Controller

## Construa um spring em java 8

## Executando testes unitários em controllers

- Antes de fazer o teste unitário vamos construir algumas camadas da aplicação

## Colocando as dependencias no pom.xml

```
<dependencies>
	<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.21</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-rest</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.jupiter</groupId>
					<artifactId>junit-jupiter</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.mockito</groupId>
					<artifactId>mockito-junit-jupiter</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
		</dependency>
	</dependencies>
```

## Atualizando as bibliotecas do ApplicationTest

Atualize as bibliotecas do arquivo ApplicationTest

![alt text](https://i.imgur.com/r5zvS0E.png)

```
package com.generation.Junit2;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Junit2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
````````

### Model/Entity 

 1. Crie um pacote e o nomeie como Model ou Entity, em seguida cole o código abaixo;
 
```
    package integracao.bancodedados.model;
    
    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.GenerationType;
    import javax.persistence.Id;
    import javax.validation.constraints.NotEmpty;
    
    @Entity
    public class ContatoModel {
    
    	//ATRIBUTOS
    	@Id
    	@GeneratedValue(strategy=GenerationType.IDENTITY)
    	private Long id;
    	
    	@NotEmpty(message="O DDD deve ser preenchido")
    	private String ddd;
    	
    	@NotEmpty(message="O Telefone deve ser preenchido")
    	private String telefone;
    	
    	@NotEmpty(message="O Nome deve ser preenchido")
    	private String nome;
    
    	//CONTRUCTORS
    	public ContatoModel(){
    	}
    	
    	public ContatoModel(String nome, String ddd, String telefone) {
    		this.nome = nome;
    		this.ddd = ddd;
    		this.telefone = telefone;
    	}
    	
    	//GETTERS AND SETTERS
    	public Long getId() {
    		return id;
    	}
    
    	public void setId(Long id) {
    		this.id = id;
    	}
    
    	public String getDdd() {
    		return ddd;
    	}
    
    	public void setDdd(String ddd) {
    		this.ddd = ddd;
    	}
    
    	public String getTelefone() {
    		return telefone;
    	}
    
    	public void setTelefone(String telefone) {
    		this.telefone = telefone;
    	}
    
    	public String getNome() {
    		return nome;
    	}
    
    	public void setNome(String nome) {
    		this.nome = nome;
    	}
    } 



````````


### Crie uma Interface de repository para usar os recursos do JPAHibernat

2. Crie um pacote e o nomeio como repository e insira o código abaixo;

```
package integracao.bancodedados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import integracao.bancodedados.model.ContatoModel;

@Repository
public interface ContatoRepository extends JpaRepository<ContatoModel, Long> {

	public ContatoModel findFirstByNome(String nome);
	public List<ContatoModel> findAllByNomeIgnoreCaseContaining(String nome);

}

`````


### Crie uma classe de controller

3. Crie um pacote com o nome de controller e dentro do pacote crie uma classe e nomeio de ContatoController, insira o código abaixo;
````
package integracao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import integracao.rest.model.ContatoModel;
import integracao.rest.repository.ContatoRepository;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

	@Autowired
	private ContatoRepository contatoRepository;

	@GetMapping
	public ResponseEntity<List<ContatoModel>> getAll() {
		List<ContatoModel> contatos = contatoRepository.findAll();
		return ResponseEntity.ok(contatos);
	}

	@GetMapping("/contato/{id}")
	public ResponseEntity<ContatoModel> getById(@PathVariable Long id) {
		return contatoRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.badRequest().build());
	}

	@PostMapping("/inserir")
	public ResponseEntity<ContatoModel> post(@RequestBody ContatoModel contato) {
		contato = contatoRepository.save(contato);
		return ResponseEntity.status(HttpStatus.CREATED).body(contato);
	}

	@PutMapping("/alterar")
	public ResponseEntity<ContatoModel> put(@RequestBody ContatoModel contato) {
		contato = contatoRepository.save(contato);
		return ResponseEntity.status(HttpStatus.OK).body(contato);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		contatoRepository.deleteById(id);		
	}

}
````

Pronto! agora já temos um projeto onde possamos implementar uma classe de teste.

### Implementando os testes unitários.

> Nesta seção iremos realizar testes unitários nas nossas anotações de
> validações inseridas na nossa repository.

3. Crie um pacote em src/test/resource e crie um arquivo do tipo file e o nomeio como  application.properties;

	**apasta ficará assim:**	
	![enter image description here](https://i.imgur.com/cGw5ZTP.png)


	em seguida acesso o arquivo **application.properties** e insira o código abaixo para configurar a conexão com o banco de dados.
````
 spring.jpa.hibernate.ddl-auto=update 
 spring.datasource.url=jdbc:mysql://localhost/test_tdd_repository?createDatabaseIfNotExist=true&serverTimezone=UTC&useSSl=false 
 spring.datasource.username=root 
 spring.datasource.password=Admin357/ 
 spring.jpa.show-sql=true`
 ````


4. Em src/test/java Crie um pacote e o nomeie como **agenda**, em seguida crie uma classe de test e a nomeie Teste e insira o código abaixo;

````
package integracao.rest.agenda;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)   
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Teste {
	
	@Autowired
	private TestRestTemplate testRestTemplate;	
	
	@Test
	public void deveMostrarTodosContatos() {
		ResponseEntity<String> resposta = testRestTemplate.exchange("/contatos/",HttpMethod.GET,null, String.class);
		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
}
````
![enter image description here](https://i.imgur.com/uHzMYJN.png)

## Para testar o app

![alt text](https://i.imgur.com/hQ2eFrI.png)

### Qualquer duvida não esqueça de pesquisar dentro projeto guia do gitHub




