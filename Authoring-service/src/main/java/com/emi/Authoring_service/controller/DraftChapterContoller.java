package com.emi.Authoring_service.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emi.Authoring_service.RequestDtos.RequestChapterCreateDto;
import com.emi.Authoring_service.RequestDtos.RequestUpdateDraftChapterDto;
import com.emi.Authoring_service.ResponseDtos.ResponseDraftChapterDto;
import com.emi.Authoring_service.service.DraftChapterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authoring/chapterDrafts")
public class DraftChapterContoller {

	private final DraftChapterService  draftChapterService;
	
	
	@GetMapping("/chapterIds")
	public ResponseEntity<List<ResponseDraftChapterDto>> getMyDraftChaptersByChapterIds(UUID authorId, Set<UUID> chaterIds){
		return ResponseEntity.ok(draftChapterService.getMyDraftChaptersByChapterIds(authorId, chaterIds));
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseDraftChapterDto>   createChapterDraft(RequestChapterCreateDto request) {
		return ResponseEntity.ok(draftChapterService.createChapterDraft(request));
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ResponseDraftChapterDto>  updateChapterDraft( RequestUpdateDraftChapterDto  request, UUID authorId) {
		return ResponseEntity.ok(draftChapterService.updateChapterDraft(request, authorId));
	}
	
	@GetMapping("/book")
	public ResponseEntity<List<ResponseDraftChapterDto>>  getMyDraftChaptersByBookId(UUID authorId, UUID bookId){
		return ResponseEntity.ok(draftChapterService.getMyDraftChaptersByBookId(authorId, bookId));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteDraftChaptersByIds(List<UUID> chapterId, UUID authorId) {
		return ResponseEntity.ok(draftChapterService.deleteDraftChaptersByIds(chapterId, authorId));
	}
	public ResponseEntity<String>  publishDraftedChapters( Set<UUID> draftChapterId, UUID authorId){
		draftChapterService.publishDraftedChapters(draftChapterId, authorId);
		return ResponseEntity.ok("Chpters published !!");
	}
}
