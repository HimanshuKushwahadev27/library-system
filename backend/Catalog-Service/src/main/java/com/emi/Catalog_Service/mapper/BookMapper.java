package com.emi.Catalog_Service.mapper;

import java.time.Instant;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.emi.Catalog_Service.Entity.Book;
import com.emi.Catalog_Service.RequestDtos.RequestBookCreationDto;
import com.emi.Catalog_Service.RequestDtos.RequsestBookUpdateDto;
import com.emi.Catalog_Service.ResponseDtos.CatalogPriceResponse;
import com.emi.Catalog_Service.ResponseDtos.ResponseBookDto;
import com.emi.Catalog_Service.ResponseDtos.ResponseFullBookDto;
import com.emi.Catalog_Service.enums.BookLifeCycleStatus;
import com.emi.Catalog_Service.enums.BookVisibilityStatus;
import com.emi.events.bookPublished.BookPublishedEvent;
import com.emi.events.bookUpdate.BookUpdatedEvent;


@Component
public class BookMapper {

	public Book toEntity(RequestBookCreationDto requestDto) {
		Book book = new Book();
		book.setTitle(requestDto.title());
		book.setDescription(requestDto.description());
		book.setISBN(requestDto.ISBN());
		book.setPrice(requestDto.price());
		book.setCreatedAt(Instant.now());
		book.setUpdatedAt(Instant.now());
		book.setTotalChapters(0);
		book.setDeleted(false);
		book.setFreePreview(requestDto.freePreviewAvailable());
		book.setStatusLifecycle(requestDto.lifeCycleStatus());
		book.setStatusVisible(requestDto.visibilityStatus());
		return book;
	}

	public ResponseBookDto toDto(Book savedBook) {
		return new ResponseBookDto(
				savedBook.getId(),
				savedBook.getPrice(),
				savedBook.getDescription(),
				savedBook.getStatusLifecycle(),
				savedBook.getStatusVisible(),
				savedBook.getTotalChapters(),
				"Book Saved in Author's publsh section Successfully (Catalog service)"
				);
			
	}
	
	public ResponseFullBookDto toFullBookDto(Book book, List<UUID> chapterIds) {
		return new ResponseFullBookDto(
				
				book.getTitle(),
				book.getDescription(),
				book.getISBN(),
				book.getPrice(),
				book.getAuthorSnapshots().stream().collect(Collectors.toList()),
				book.getGenreIds().stream().collect(Collectors.toList()),
				book.getStatusLifecycle(),
				book.getStatusVisible(),
				book.getTotalChapters(),
				chapterIds,
				book.getFreePreview()
				);
	}
	
	public Book updateBookEntity(RequsestBookUpdateDto request, Book book) {
		book.setPrice(request.price());
		book.setDescription(request.description());
		book.setFreePreview(request.freePreviewAvailable());
		book.setUpdatedAt(Instant.now());
		return book;
	}
	
	public ResponseBookDto returnUpdatedBook(RequsestBookUpdateDto request, Integer totalChapters) {
		return new ResponseBookDto(
				request.bookId(),
				request.price(),
				request.description(),
				request.lifeCycleStatus(),
				request.visibilityStatus(),
				totalChapters,
				"Book Saved in Author's publsh section Successfully (Catalog service)"				
				);
	}
	
	
	public BookPublishedEvent  emitCreateBookEvent(Book book) {
		
		BookPublishedEvent event = BookPublishedEvent.newBuilder()
		        .setBookId(book.getId().toString())
		        .setTitle(book.getTitle())
		        .setDescription(book.getDescription())
		        .setPrice(book.getPrice().doubleValue())
		        .setAuthorNames(book.getAuthorSnapshots().stream().map(t -> (CharSequence)t.getName()).toList())
		        .setGenres(book.getGenreIds().stream().map(t -> (CharSequence)t.getName()).toList())
		        .setFreePreviewAvailable(book.getFreePreview())
		        .setPublishedAt(System.currentTimeMillis())
		        .build();
		
		if(book.getStatusLifecycle()==BookLifeCycleStatus.ONGOING) {
			event.setLifeCycleStatus(com.emi.events.bookPublished.BookLifeCycleStatus.ONGOING);
		}else {
			event.setLifeCycleStatus(com.emi.events.bookPublished.BookLifeCycleStatus.COMPLETED);
		}
		
		if(book.getStatusVisible() == BookVisibilityStatus.PUBLIC) {
			event.setVisibilityStatus(com.emi.events.bookPublished.BookVisibilityStatus.PUBLIC);
		}else {
			event.setVisibilityStatus(com.emi.events.bookPublished.BookVisibilityStatus.PRIVATE);
		}
		
		return event;
	}
	
	public BookUpdatedEvent emitUpdateEvent(Book book) {
		BookUpdatedEvent event = BookUpdatedEvent.newBuilder()
		        .setBookId(book.getId().toString())
		        .setTitle(book.getTitle())
		        .setDescription(book.getDescription())
		        .setPrice(book.getPrice().doubleValue())
		        .setFreePreview(book.getFreePreview())
		        .setUpdatedAt(System.currentTimeMillis())
		        .build();
		
		if(book.getStatusLifecycle()==BookLifeCycleStatus.ONGOING) {
			event.setLifeCycleStatus(com.emi.events.bookUpdate.BookLifeCycleStatus.ONGOING);
		}else {
			event.setLifeCycleStatus(com.emi.events.bookUpdate.BookLifeCycleStatus.COMPLETED);
		}
		
		if(book.getStatusVisible() == BookVisibilityStatus.PUBLIC) {
			event.setVisibilityStatus(com.emi.events.bookUpdate.BookVisibilityStatus.PUBLIC);
		}else {
			event.setVisibilityStatus(com.emi.events.bookUpdate.BookVisibilityStatus.PRIVATE);
		}
		
		return event;
	}

	public CatalogPriceResponse toCatalogPriceResponse(Book book, List<UUID> chapterIds) {
	return new CatalogPriceResponse(
			book.getId(),
			chapterIds,
			book.getPrice()
			);
	}
}
