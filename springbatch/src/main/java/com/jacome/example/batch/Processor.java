package com.jacome.example.batch;

import org.springframework.batch.item.ItemProcessor;

/** 
 * Generics I , O
 * I = Input que será processado
 * O = Output
 * @author diego
 *
 */
public class Processor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		System.out.println("Inside Processor");
		return "PROCESSED "+item.toUpperCase();
	}

}
