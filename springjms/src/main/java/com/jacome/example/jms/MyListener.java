package com.jacome.example.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

	// Escuta a fila passado no destino
	@JmsListener(destination = "${spring.jms.myqueue}")
	public void receive(String message) {
		System.out.println("Message received ===> "+message);		
	}
}
