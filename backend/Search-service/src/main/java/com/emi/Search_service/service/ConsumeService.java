package com.emi.Search_service.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.emi.Search_service.Repo.BookDocumentRepo;
import com.emi.Search_service.document.BookDocument;
import com.emi.Search_service.exceptions.DocumentNotFoundException;
import com.emi.events.BookDeletedEvent;
import com.emi.events.BookPublishedEvent;
import com.emi.events.BookUpdatedEvent;
import com.emi.events.BookVisibilityStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumeService {

	private final BookDocumentRepo bookDocumentRepo;
	
	@KafkaListener(topics = "Book-create-event", groupId="searchService")
	public void listenCreateBook(BookPublishedEvent event) {
		BookDocument document  = BookDocument.builder()
				.bookId((String)event.getBookId())
				.authorName(event.getAuthorNames().stream().map(t -> t.toString()).toList())
				.description((String)event.getDescription())
				.title((String)event.getTitle())
				.price(event.getPrice())
				.genreName(event.getAuthorNames().stream().map(t -> t.toString()).toList())
				.freePreview(event.getFreePreviewAvailable())
				.publishedAt(event.getPublishedAt())
				.updatedAt(event.getPublishedAt())
				.lifeCycleStatus(event.getLifeCycleStatus().toString())
				.visibilityStatus(event.getVisibilityStatus().toString())
				.build();
		
		bookDocumentRepo.save(document);
	}
	
	@KafkaListener(topics = "Book-update-event", groupId="searchService")
	public void listenUpdateBook(BookUpdatedEvent event) {
	    BookDocument doc = bookDocumentRepo
	            .findByBookId(event.getBookId().toString())
	            .orElseThrow(() -> new DocumentNotFoundException("no Doc for the given book id +" + event.getBookId()));
	    
	    doc.setDescription((String)event.getDescription());
	    doc.setFreePreview(event.getFreePreview());
	    doc.setLifeCycleStatus(event.getLifeCycleStatus().toString());
	    doc.setPrice(event.getPrice());
	    doc.setVisibilityStatus(event.getVisibilityStatus().toString());
	    doc.setUpdatedAt(event.getUpdatedAt());
	    doc.setTitle((String)event.getTitle());
	    
	    bookDocumentRepo.save(doc);
	}
	
	@KafkaListener(topics = "Book-delete-event", groupId="searchService")
	public void listenDeleteBook(BookDeletedEvent event){
	    BookDocument doc = bookDocumentRepo
	            .findByBookId(event.getBookId().toString())
	            .orElseThrow(() -> new DocumentNotFoundException("no Doc for the given book id +" + event.getBookId()));
	    
	    doc.setVisibilityStatus(BookVisibilityStatus.DELETE.toString());
	    
	    bookDocumentRepo.save(doc);
	}
}
