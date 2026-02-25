package com.emi.Search_service.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.emi.Search_service.exceptions.DocumentNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DocumentNotFoundException.class)
	public ResponseEntity<?> handleBookNotFound(DocumentNotFoundException ex){
		return ResponseEntity
				.status(404)
				.body(ex.getMessage());
	}
}
