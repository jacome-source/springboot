package com.jacome.example.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

	@Autowired
	private StepBuilderFactory sbf;
	@Autowired
	private JobBuilderFactory jbf;
	
	
	@Bean
	public Job job() {
		// Incrementer = Sempre que um job é criado, gera um novo ID para ele
		// Listener = classe a ser executada antes e depois do job rodar
		// Step = steps do job
		return jbf.get("job1")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.start(step())
				.build();
	}
	
	@Bean
	public Step step() {
		// Chunke = Qtd de threads
		return sbf.get("step1")
				.<String,String>chunk(1)
				.reader(reader())
				.writer(writer())
				.processor(processor())
				.build();
	}
	
	@Bean
	public Reader reader() {
		return new Reader();
	}
	
	@Bean
	public Writer writer() {
		return new Writer();
	}
	
	@Bean
	public Processor processor() {
		return new Processor();
	}
	
	@Bean
	public MyJobListener listener() {
		return new MyJobListener();
	}
	
}
