package com.emi.Author_Service;

import org.springframework.boot.SpringApplication;



public class TestAuthorServiceApplication {
	public static void main(String[] args) {
		SpringApplication.from(AuthorServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
