package com.jacome.example.rest.fileprocessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class RestfileprocessingApplicationTests {

	private static final String DOWNLOAD_PATH = "C:/Users/diego/Documents/workspace_estudo/curso_springboot/restfileprocessing/src/main/resources/downloadedfiles/";
	private static final String DOWNLOAD_URL = "http://localhost:8080/download/";
	private static final String FILE_UPLOAD_URL = "http://localhost:8080/upload";
	
	// Precisa ser declarado com Bean
	// Declarado no SpringBootApplication
	@Autowired
	RestTemplate restTemplate;
	
	@Test
	void testUpload() {
		// Cabeçalho da Requisição
		// Informa o content type
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		// Corpo da requisição
		// No postman seria a tupla (file, e arquivo de foto)
		// É um MultipartFile
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
		// Arquivo se encontra nos resources
		body.add("file", new ClassPathResource("diego.medeiros.jpg"));
		
		// Entidade chave, representa a chamada HTTP (request ou response)
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String,Object>>(body,headers);
		
		// Faz a chamada post passando o body e recebe de resposta um boolean
		ResponseEntity<Boolean> response = restTemplate.postForEntity(FILE_UPLOAD_URL, httpEntity, Boolean.class);
		System.out.println(response.getBody());
	}

	
	@Test
	void testDownload() throws IOException {
		// Cabeçalho da Requisição
		HttpHeaders headers = new HttpHeaders();
		// Header Accept para informar o que espera na resposta
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
		
		// Não é necessário corpo na chamada get
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		
		// Usado para compor a requisição
		String fileName = "diego.medeiros.jpg";
		
		ResponseEntity<byte[]> response = restTemplate.exchange(DOWNLOAD_URL+fileName, HttpMethod.GET, httpEntity, byte[].class);
		
		// Escreve o arquivo do corpo da requisição no path de download
		Files.write(Paths.get(DOWNLOAD_PATH+fileName),response.getBody());		
	}
}
