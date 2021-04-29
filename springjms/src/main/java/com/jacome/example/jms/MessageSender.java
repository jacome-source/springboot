package com.jacome.example.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	// Nome da fila
	// Ao receber uma mensagem o broker cria a fila automaticamente
	// Geralmente, as filas são criadas por scripts de devOps
	@Value("${spring.jms.myqueue}")
	private String queue;
	
	public void send(String message) {
		System.out.println("Message sent ===> "+message);
		// Seta a estratégia do broker para pub/sub
		// jmsTemplate.setPubSubDomain(true);
		jmsTemplate.convertAndSend(queue, message);
	}
	
	public void sendMessageCreator(String message) {
		System.out.println("Message sent ===> "+message);
		// Capaz de passar headers pela mensagem
		MessageCreator mc = s -> s.createTextMessage(message);
		jmsTemplate.convertAndSend(queue, mc);
	}
}
