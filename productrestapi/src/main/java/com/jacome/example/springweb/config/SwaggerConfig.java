package com.jacome.example.springweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Classe de configuração
 * Restringe endpoints que serão documentados, removendo os actuators
 * @author diego
 *
 */
@Configuration
public class SwaggerConfig {

		@Bean
		public Docket productApi() {
					// Docket é o arquivo de configuração do springfox
			return new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(apiInfo()).select()
					// RequestHandlerSelectors limita os endpoints
					// any() - engloba todos os endpoints
					// none() - não adiciona nenhum endpoint
					// basedPackage(package) - adiciona os endpoints apenas do package específico 
					.apis(RequestHandlerSelectors.basePackage("com.jacome.example.springweb.controllers"))
					// É possível filtrar mais fundo pelos paths (URLs dos endpoints)
					// PathSelector recupera os paths através de expressão regular
					.paths(PathSelectors.regex("/productapi/products/.*")).build();
		}

		private ApiInfo apiInfo() {
			return new ApiInfoBuilder()
						.title("Product API")
						.description("Product CRUD Operations")
						.termsOfServiceUrl("Open Source")
						.license("Jacome License")
						.licenseUrl("jacome.com")
						.version("4.0")
						.build();
		}
	
}
