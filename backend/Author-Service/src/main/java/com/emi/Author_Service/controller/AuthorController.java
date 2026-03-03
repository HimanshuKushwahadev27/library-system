package com.emi.Author_Service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emi.Author_Service.requestDto.RequestAuthorDto;
import com.emi.Author_Service.responseDto.ResponseAuthorDto;
import com.emi.Author_Service.responseDto.ResponseAuthorDtoForAdmin;
import com.emi.Author_Service.service.AuthorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {

	private final AuthorService authorService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseAuthorDto> register(
			@RequestBody @Valid RequestAuthorDto request,
			@AuthenticationPrincipal Jwt jwt) {
		return ResponseEntity.ok(authorService.register(request, UUID.fromString(jwt.getSubject())));
	}
	
	@PatchMapping("/update/{authorID}")
	public ResponseEntity<ResponseAuthorDto> update(
			@RequestBody @Valid RequestAuthorDto request,
			@PathVariable  UUID authorID, 
			@AuthenticationPrincipal Jwt jwt) {
		return ResponseEntity.ok(authorService.update(request, authorID, UUID.fromString(jwt.getSubject())));
	}
	
	@DeleteMapping("/delete/{authorId}")
	public ResponseEntity<String> delete(
			@PathVariable UUID authorId,
			@AuthenticationPrincipal Jwt jwt) {
		return ResponseEntity.ok(authorService.delete(authorId, UUID.fromString(jwt.getSubject())));
	}
	
	
	@GetMapping("/get/{authorId}")
	public ResponseEntity<ResponseAuthorDto> get(@PathVariable UUID authorId,
			@AuthenticationPrincipal Jwt jwt) {
		
		return ResponseEntity.ok(authorService.get(authorId, UUID.fromString(jwt.getSubject())));
	}
	
	@GetMapping("/gets")
	public ResponseEntity<List<ResponseAuthorDtoForAdmin>> getAuthors(){
		return ResponseEntity.ok(authorService.getAuthors());

	}
}
