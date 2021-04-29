package com.jacome.example.springweb.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.jacome.example.springweb.dto.ErrorResponse;
import com.jacome.example.springweb.entities.Product;
import com.jacome.example.springweb.repositories.ProductRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class ProductRestController {

	@Autowired
	private ProductRepository productRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

	// @ApiOperation -> Anotação para Swagger API
	@ApiOperation(value="Retrieves all the products",
					notes="A list of products",
					response=Product.class,
					responseContainer="List",
					produces="application/json" 
				)
	@RequestMapping(value="/products/", method=RequestMethod.GET)
	public List<Product> getProducts(){
		return productRepository.findAll();
	}
	
	@GetMapping(value="/products/{id}")
	@Transactional(readOnly = true)
	@Cacheable("product-cache")
	public Product getProduct(@PathVariable("id") int id) {
		LOGGER.info("Find product by ID: "+id);
		return productRepository.findById(id).get();
	}
	
	// @Valid -> Anotação para validar dados na requisição
	@RequestMapping(value="/products/", method=RequestMethod.POST)
	public Product createProduct(@Valid @RequestBody Product product) {
		return productRepository.save(product);
	}
	
	@RequestMapping(value="/products/", method=RequestMethod.PUT)
	public Product updateProduct(@Valid @RequestBody Product product) {
		return productRepository.save(product);
	}
	
	@RequestMapping(value="/products/{id}", method=RequestMethod.DELETE)
	@CacheEvict("product-cache")
	public void deleteProduct(@PathVariable("id") int id){
		productRepository.deleteById(id);
	}
	
	// Customiza mensagem de erro
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<List<ErrorResponse>> handleUserMethodFieldErrors(MethodArgumentNotValidException ex, WebRequest request) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ErrorResponse> errors = new ArrayList<ErrorResponse>();
		for (FieldError fieldError : fieldErrors) {
			String field = fieldError.getField();
			String message = fieldError.getDefaultMessage();
			ErrorResponse error = new ErrorResponse(field,message);
			errors.add(error);
		}
		return ResponseEntity.badRequest().body(errors);
	}
}
