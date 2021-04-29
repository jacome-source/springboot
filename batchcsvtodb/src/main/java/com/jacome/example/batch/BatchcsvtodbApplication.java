package com.jacome.example.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchcsvtodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchcsvtodbApplication.class, args);
	}

}
