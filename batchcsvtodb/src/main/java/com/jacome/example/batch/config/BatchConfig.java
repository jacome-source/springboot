package com.jacome.example.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.jacome.example.batch.models.Product;

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
		return jbf.get("j2")
				.incrementer(new RunIdIncrementer())
				.start(step())
				.build();
	}
	
	@Bean
	public Step step() {
		// Chunke = Qtd de threads
		return sbf.get("s1")
				.<Product,Product>chunk(3)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	
	@Bean
	public ItemReader<Product> reader () {
		
		// Classe para leitura do csv
		var reader = new FlatFileItemReader<Product>();
		// Classe pra leitura de linhas
		var lineMapper = new DefaultLineMapper<Product>();
		// Classe para separar os campos do csv em suas colunas  
		var lineTokenizer = new DelimitedLineTokenizer();
		// Classe para associar as colunas do csv com objeto
		var fieldSetMapper = new BeanWrapperFieldSetMapper<Product>();
		
		
		// Informa arquivo a ser lido
		reader.setResource(new ClassPathResource("products.csv"));
		// Informa regras de divisão do csv
		lineTokenizer.setNames("id","name","description","price");
		// Informa classe gerada pelo csv
		fieldSetMapper.setTargetType(Product.class);
		
		
		// Setando o tokenizer e o setMapper no leitor de linhas
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		reader.setLineMapper(lineMapper);
		
		return reader;
	}
	

	@Bean	
	public ItemProcessor<Product,Product> processor() {
		return p -> { 
			p.setPrice(p.getPrice()-p.getPrice()*10/100); 
			return p;
		};
	}
	
	
	@Bean	
	public ItemWriter<Product> writer() {
		JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<Product>();
		writer.setDataSource(dataSource());
		// Associa objeto e suas propriedades ao sql
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
		writer.setSql("insert into product (name,description,price) values (:name,:description,:price)");
		return writer;
	}
	
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
		dataSource.setUsername("root");
		dataSource.setPassword("dj881415");
		return dataSource;
	}
}
