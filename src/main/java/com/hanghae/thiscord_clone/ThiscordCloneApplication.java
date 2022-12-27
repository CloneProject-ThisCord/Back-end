package com.hanghae.thiscord_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ThiscordCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThiscordCloneApplication.class, args);
	}

}
