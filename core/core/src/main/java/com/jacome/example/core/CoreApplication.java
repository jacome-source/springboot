package com.jacome.example.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Nome da classe derivado do nome do projeto
 * @SpringBootApplication funciona como se fosse várias anotações (pousar o mouse em cima, para ver as anotações)
 * @SpringBootConfiguration informa que a classe pode definir vários beans
 * @EnableAutoConfiguration informa para o spring boot configurar automaticamente a aplicação baseado nas dependências do classpath
 * @ComponentScan spring boot deve varrer as classes verificando suas respectivas anotações: service, repository, etc e criar beans dessas anotações

 * 
 * @author diego
 *
 */
@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		
		/**
		 * Run, recebe qualquer classe anotada com @SpringBootApplication
		 */
		SpringApplication.run(CoreApplication.class, args);
	}

}
