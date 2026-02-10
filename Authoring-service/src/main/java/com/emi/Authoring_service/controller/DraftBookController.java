package com.emi.Authoring_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emi.Authoring_service.RequestDtos.PublishDraftBookRequest;
import com.emi.Authoring_service.RequestDtos.RequestBookCreateDto;
import com.emi.Authoring_service.RequestDtos.RequestUpdateDraftBookDto;
import com.emi.Authoring_service.ResponseDtos.ResponseDraftBookDto;
import com.emi.Authoring_service.service.DraftBookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authoring/bookDrafts")
public class DraftBookController {

	private final DraftBookService draftBookService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDraftBookDto> createBookDraft(@RequestBody @Valid RequestBookCreateDto request) {
		return ResponseEntity.ok(draftBookService.createBookDraft(request));
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ResponseDraftBookDto> updateBookDraft( RequestUpdateDraftBookDto  request) {
		return ResponseEntity.ok(draftBookService.updateBookDraft(request));
	}
	@GetMapping("/books")
	public ResponseEntity<List<ResponseDraftBookDto>>  getMyDraftBooks(UUID authorId){
		return ResponseEntity.ok(draftBookService.getMyDraftBooks(authorId));
	}
	@GetMapping("/book/{authorId}/{draftBookId}")
	public ResponseEntity<ResponseDraftBookDto>  getMyDraftBooksById(UUID authorId, UUID draftBookId){
		return ResponseEntity.ok(draftBookService.getMyDraftBooksById(authorId, draftBookId));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteDraftBookById(UUID bookId, UUID authorId) {
		return ResponseEntity.ok(draftBookService.deleteDraftBookById(bookId, authorId));
	}
	
	@PostMapping("/publish")
	public ResponseEntity<String> publishDraftedBook( PublishDraftBookRequest request, UUID authorId) {
		draftBookService.publishDraftedBook(request, authorId);
		return ResponseEntity.ok("Book published successfully");
	}
	

	@PatchMapping("/updatePublish")
	public ResponseEntity<String> updatePublishedBook( RequestUpdateDraftBookDto request) {
		draftBookService.updatePublishedBook(request);
		return ResponseEntity.ok("Book Updated Successfully");
	}
}
