package com.example.WineOclocK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableJpaAuditing
@SpringBootApplication
public class WineOclocKApplication {

	public static void main(String[] args) {
		SpringApplication.run(WineOclocKApplication.class, args);
	}

}
