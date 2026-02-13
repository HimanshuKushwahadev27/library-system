package com.emi.Authoring_service.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.emi.Authoring_service.exceptions.BookAlreadyExistsException;
import com.emi.Authoring_service.exceptions.ChapterDraftExistsException;
import com.emi.Authoring_service.exceptions.DraftNotFoundException;
import com.emi.Authoring_service.exceptions.NotAuthorizedException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BookAlreadyExistsException.class)
	public ResponseEntity<?> handleBookDuplication(BookAlreadyExistsException ex){
		return ResponseEntity
				.status(404)
				.body(ex.getMessage());
	}
	
	@ExceptionHandler(DraftNotFoundException.class)
	public ResponseEntity<?> handleBookDraftNotFound(DraftNotFoundException ex){
		return ResponseEntity
				.status(404)
				.body(ex.getMessage());
	}
	
	@ExceptionHandler(NotAuthorizedException.class)
	public ResponseEntity<?> notAuthorized(NotAuthorizedException ex){
		return ResponseEntity
				.status(404)
				.body(ex.getMessage());
	}
	
	@ExceptionHandler(ChapterDraftExistsException.class)
	public ResponseEntity<?> chapterDraftExistsHandling(ChapterDraftExistsException ex){
		return ResponseEntity
				.status(404)
				.body(ex.getMessage());
	}
	
}
