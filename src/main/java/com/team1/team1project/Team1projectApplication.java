package com.team1.team1project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Team1projectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team1projectApplication.class, args);
	}

}
