package com.jacome.example.core;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jacome.example.core.service.PaymentServiceImpl;

/**
 * @SpringBootTest busca a classe anotada com @SpringBootApplication e cria um context contendo todos os beans dessa aplicação
 * Permite utilizar autowired dos beans do @SpringBootApplication na classe de testes
 * 
 * @author diego
 *
 */
@SpringBootTest
class CoreApplicationTests {

	@Autowired
	PaymentServiceImpl service;
	
	@Test
	void testDependencyInjection() {
		assertNotNull(service.getDao());
	}

}
