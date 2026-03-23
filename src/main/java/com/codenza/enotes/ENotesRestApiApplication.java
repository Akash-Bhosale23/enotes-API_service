package com.codenza.enotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
public class ENotesRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ENotesRestApiApplication.class, args);
	}

}
