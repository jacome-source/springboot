package com.jacome.example.springweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;

/** Configuração de Cache **/
/** Cache Evict = Limpeza de cache **/
@Configuration
public class ProductCacheConfig {

	@Bean
	public Config cacheConfig() {
		return new Config()
					.setInstanceName("hazel-instance")
					.addMapConfig(new MapConfig()						
									.setName("product-cache") // Nome do cache
									.setTimeToLiveSeconds(3000) // Tempo de vida do cache
									.setEvictionConfig(new EvictionConfig() // Configurações para evitar estouro do cache
											.setSize(200) // Qtd de Objetos do Cache
											.setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE) // Se baseia no espaço disponível na memória
											.setEvictionPolicy(EvictionPolicy.LRU) // Least Recently Used
									)
					);					
	}
	
}
