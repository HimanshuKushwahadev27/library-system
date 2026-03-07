package com.emi.Author_Service;


import com.emi.Author_Service.requestDto.RequestAuthorDto;

public class TestFactory {

	public static RequestAuthorDto validAuthor() {
		return new RequestAuthorDto(
				"Himanshu Kushwaha",
				"Backend engineer and microservices enthusiast writing about distributed systems.",
				"https://cdn.library.com/authors/profile123.png"
				);
	}

	public static RequestAuthorDto validAuthorUpdate() {
		return new RequestAuthorDto(
		"Kushwaha",
		"nope nope nope",
		"https://cdn.library.com/authors/profile123.png"
		);
	}

}
