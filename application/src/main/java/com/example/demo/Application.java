package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.domain.repo")
@EntityScan(basePackages = "com.domain.model")
@ComponentScan(basePackages = { "com.dc.facade.fd",
		"com.dc.api.controller",
		"com.dc.facade.fd",
		"com.dc.facadeImpl.fi",
		"com.domain.service",
		"com.domain.serviceImpl",
		"com.domain.repo",
		"com.domain.model",
		"com.domain.util"
})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}



}
