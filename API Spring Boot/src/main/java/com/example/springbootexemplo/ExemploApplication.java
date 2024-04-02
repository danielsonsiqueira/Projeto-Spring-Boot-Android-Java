package com.example.springbootexemplo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.springbootexemplo")
@EnableAutoConfiguration
public class ExemploApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExemploApplication.class, args);
	}

}