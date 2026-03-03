package com.emi.Catalog_Service.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emi.Catalog_Service.RequestDtos.RequestBookCreationDto;
import com.emi.Catalog_Service.RequestDtos.RequsestBookUpdateDto;
import com.emi.Catalog_Service.ResponseDtos.CatalogPriceResponse;
import com.emi.Catalog_Service.ResponseDtos.ResponseBookDto;
import com.emi.Catalog_Service.ResponseDtos.ResponseFullBookDto;
import com.emi.Catalog_Service.Services.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

	private final BookService bookService;
	
	@PostMapping(value="/create")
	public UUID createBook(
			@RequestBody @Valid RequestBookCreationDto request){
		return bookService.createBook(request);
	}
	
	@GetMapping(value="/{bookId}")
	public ResponseEntity<ResponseFullBookDto> getBook(
			@PathVariable UUID bookId,
			@AuthenticationPrincipal Jwt jwt){
		return ResponseEntity.ok(bookService.getBookById(bookId, UUID.fromString(jwt.getSubject())));
	}
	
	@GetMapping(value="/internal/{bookId}")
	public CatalogPriceResponse getBookInternal(
			@PathVariable UUID bookId){
		return bookService.getBookByIdInternal(bookId);
	}
	
	@GetMapping
	public ResponseEntity<List<ResponseFullBookDto>> listsOfBooks(
			@RequestParam List<UUID> bookIds,
			@AuthenticationPrincipal Jwt jwt){
		return ResponseEntity.ok(bookService.getBookByIds(bookIds, UUID.fromString(jwt.getSubject())));
	}
	
	@PatchMapping(value="/update/{authorId}")
	public ResponseEntity<ResponseBookDto> updateBook(
			@RequestBody @Valid RequsestBookUpdateDto request, @PathVariable UUID authorId){
		return ResponseEntity.ok(bookService.updateBook(request, authorId));
	}
	
	@DeleteMapping(value="/{bookId}/{authorId}")
	public ResponseEntity<String> deleteBook(
			@PathVariable UUID bookId, @PathVariable UUID authorId){
		return ResponseEntity.ok(bookService.deleteBook(bookId,authorId));
	}
}
