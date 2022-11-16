package com.example.WineOclocK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ComponentScan(basePackages="com.example.WineOclocK.spring.controller")
@EnableJpaAuditing
@SpringBootApplication
public class WineOclocKApplication {

	public static void main(String[] args) {
		SpringApplication.run(WineOclocKApplication.class, args);
	}

}
