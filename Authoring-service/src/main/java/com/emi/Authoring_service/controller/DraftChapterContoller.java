package com.emi.Authoring_service.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emi.Authoring_service.RequestDtos.RequestChapterCreateDto;
import com.emi.Authoring_service.RequestDtos.RequestUpdateDraftChapterDto;
import com.emi.Authoring_service.ResponseDtos.ResponseDraftChapterDto;
import com.emi.Authoring_service.service.DraftChapterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authoring/chapterDrafts")
public class DraftChapterContoller {

	private final DraftChapterService  draftChapterService;
	
	
	@GetMapping("/chapterIds/{authorId}")
	public ResponseEntity<List<ResponseDraftChapterDto>> getMyDraftChaptersByChapterIds(@PathVariable UUID authorId, @RequestParam Set<UUID> chaterIds){
		return ResponseEntity.ok(draftChapterService.getMyDraftChaptersByChapterIds(authorId, chaterIds));
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseDraftChapterDto>   createChapterDraft(@RequestBody @Valid RequestChapterCreateDto request) {
		return ResponseEntity.ok(draftChapterService.createChapterDraft(request));
	}
	
	@PatchMapping("/update/{authorId}")
	public ResponseEntity<ResponseDraftChapterDto>  updateChapterDraft( @RequestBody @Valid RequestUpdateDraftChapterDto  request,@PathVariable UUID authorId) {
		return ResponseEntity.ok(draftChapterService.updateChapterDraft(request, authorId));
	}
	
	@GetMapping("/book/{authorId}/{bookId}")
	public ResponseEntity<List<ResponseDraftChapterDto>>  getMyDraftChaptersByBookId(@PathVariable UUID authorId,@PathVariable  UUID bookId){
		return ResponseEntity.ok(draftChapterService.getMyDraftChaptersByBookId(authorId, bookId));
	}
	
	@DeleteMapping("/delete/{authorId}")
	public ResponseEntity<String> deleteDraftChaptersByIds(@RequestParam List<UUID> chapterId,@PathVariable  UUID authorId) {
		return ResponseEntity.ok(draftChapterService.deleteDraftChaptersByIds(chapterId, authorId));
	}
	
	@PostMapping("/publish/{authorId}")
	public ResponseEntity<String>  publishDraftedChapters( @RequestParam Set<UUID> draftChapterId, @PathVariable UUID authorId){
		draftChapterService.publishDraftedChapters(draftChapterId, authorId);
		return ResponseEntity.ok("Chpters published !!");
	}
}
