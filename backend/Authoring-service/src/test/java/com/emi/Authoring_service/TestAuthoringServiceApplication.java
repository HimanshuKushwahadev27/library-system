package com.emi.Authoring_service;

import org.springframework.boot.SpringApplication;



public class TestAuthoringServiceApplication {
	public static void main(String[] args) {
		SpringApplication.from(AuthoringServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}
