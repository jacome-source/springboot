package com.jacome.example.springweb.healthchecks;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		boolean error = false;
		if (error) {
			// Derruba a aplicação e exibe mensagem de erro customizada
			return Health.down().withDetail("error", "teste").build();
		}
		// Sobe a aplicação
		return Health.up().build();
	}

}
