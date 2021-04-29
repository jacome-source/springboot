package com.jacome.example.springweb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.jacome.example.springweb.entities.Product;

@SpringBootTest
class ProductrestapiApplicationTests {

	// Springboot importa automaticamente valor da URL do application.properties
	// Deve ser utilizado na existência de diferentes ambientes
	@Value("${productrestapi.services.url}")
	private String baseURL;
	
		
//	@Test
	void testGetproduct() {
		System.out.println(baseURL);
		RestTemplate restTemplate = new RestTemplate();
		// Faz a chamada no URL e deserializa automaticamente o body na classe passada por parâmetro
		Product product = restTemplate.getForObject(baseURL+"3", Product.class);
		assertNotNull(product);
		assertEquals("Celular", product.getName());
	}
	
	// Possível rodar unicamente um método do JUNIT selecionando e clicando em Run as
	// Removido pra não criar produto ao construir com maven
	//	@Test
	void testCreateproduct() {
		RestTemplate restTemplate = new RestTemplate();
		Product product = new Product();
		product.setName("PC");
		product.setDescription("Importante!");
		product.setPrice(70000);
		// Faz a chamada no URL e serializa automaticamente o objeto do parâmento
		Product newProduct = restTemplate.postForObject(baseURL, product, Product.class);
		assertNotNull(newProduct);
		assertNotNull(newProduct.getId());
		assertEquals("PC", newProduct.getName());
	}
	
//	@Test
	void testUpdateroduct() {
		RestTemplate restTemplate = new RestTemplate();
		Product product = restTemplate.getForObject(baseURL+"3", Product.class);
		product.setPrice(3000);
		// Faz a chamada no URL e serializa automaticamente o objeto do parâmento
		restTemplate.put(baseURL, product);
		Product newproduct = restTemplate.getForObject(baseURL+"3", Product.class);
		assertEquals(3000, newproduct.getPrice());
	}

}
