package com.example.auth_no_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AuthNoSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthNoSecurityApplication.class, args);
	}

}
