package com.example.WineOclocK;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableBatchProcessing
@SpringBootApplication
public class WineOclocKApplication {
	public static void main(String[] args) {
		SpringApplication.run(WineOclocKApplication.class, args);
	}

}
