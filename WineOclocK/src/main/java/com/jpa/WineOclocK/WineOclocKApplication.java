package com.jpa.WineOclocK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WineOclocKApplication {

	public static void main(String[] args) {
		SpringApplication.run(WineOclocKApplication.class, args);
	}

}
