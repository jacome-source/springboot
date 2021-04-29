package com.jacome.example.springweb;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jacome.example.springweb.entities.Product;
import com.jacome.example.springweb.repositories.ProductRepository;

// Configura a web layer necessária para os testes
// Permite o acesso aos controllers sem subir o servidor isolando o componente
@WebMvcTest
class ProductRestControllerMvcTest {

	private static final String PRODUCTS_URL = "/productapi/products/";
	private static final String CONTEXT_URL = "/productapi";
	private static final int PRODUCT_ID = 1;
	private static final String PRODUCT_DESCRIPTION = "Bom";
	private static final String PRODUCT_NAME = "PC";
	private static final int PRODUCT_PRICE = 2000;
	
	// Usado paras chamadas REST
	@Autowired
	private MockMvc mockMvc;
	// Mockando o repositório
	@MockBean
	private ProductRepository productRepository;
	
	// Teste Unitário testa um único componente
	// Necessário mockar todas as outras dependências
	// Mockando o repository
	@Test
	void testFindAll() throws Exception {
		List<Product> products = createTestList();
		when(productRepository.findAll()).thenReturn(products);
		

		// Conversos de json para objeto
		ObjectWriter jsonConverter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		// get(PRODUCTS_URL).contextPath(CONTEXT_URL) = chamada de verbo HTTP
		// MockMvc acessa o controller da URI através do DispatcherServlet sem subir o servidor
		// Retorna o status code http do método conforme o repository mockado acima
		mockMvc.perform(get(PRODUCTS_URL).contextPath(CONTEXT_URL))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonConverter.writeValueAsString(products)));
//				.andExpect(content().json("[{'id': 1,'name':'PC','description':'Bom','price':2000}]"));
	}


	@Test
	void testCreateProduct() throws Exception {
		Product product = createProduct();
		ObjectWriter jsonConverter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		// Mock do repository, utilizando any() do mockito
		// Qualquer coisa passada no save retorna product
		// Deve ser any e não uma entidade instanciada, pois o equals não irá bater
		when(productRepository.save(any())).thenReturn(product);
		// No método post é necessário informar o content type e o corpo da requisição
		mockMvc.perform(post(PRODUCTS_URL).contextPath(CONTEXT_URL).contentType(MediaType.APPLICATION_JSON)
				.content(jsonConverter.writeValueAsString(product)))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonConverter.writeValueAsString(product)));;
	}
	
	@Test
	void testUpdateProduct() throws Exception {
		Product product = createProduct();
		product.setPrice(1200);
		ObjectWriter jsonConverter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		// Mock do repository, utilizando any() do mockito
		// Qualquer coisa passada no save retorna product
		// Deve ser any e não uma entidade instanciada, pois o equals não irá bater
		when(productRepository.save(any())).thenReturn(product);
		// No método post é necessário informar o content type e o corpo da requisição
		mockMvc.perform(put(PRODUCTS_URL).contextPath(CONTEXT_URL).contentType(MediaType.APPLICATION_JSON)
				.content(jsonConverter.writeValueAsString(product)))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonConverter.writeValueAsString(product)));;
	}
	
	@Test
	void testDeleteProduct() throws Exception {
		doNothing().when(productRepository).deleteById(PRODUCT_ID);
		mockMvc.perform(delete(PRODUCTS_URL+PRODUCT_ID).contextPath(CONTEXT_URL)).andExpect(status().isOk());

	}
	
	private List<Product> createTestList() {
		// Extraído com: Refactor -> Extract constant
		Product p = createProduct();
		List<Product> list = Arrays.asList(p);
		return list;
	}


	private Product createProduct() {
		Product p = new Product(PRODUCT_ID,PRODUCT_NAME,PRODUCT_DESCRIPTION,PRODUCT_PRICE);
		return p;
	}

}
