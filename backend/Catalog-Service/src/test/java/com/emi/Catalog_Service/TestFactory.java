package com.emi.Catalog_Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.emi.Catalog_Service.RequestDtos.RequestBookCreationDto;
import com.emi.Catalog_Service.RequestDtos.RequestCreateContentDto;
import com.emi.Catalog_Service.enums.BookLifeCycleStatus;
import com.emi.Catalog_Service.enums.BookVisibilityStatus;

public class TestFactory {

	public static RequestBookCreationDto validBookRequest(UUID genreId1, UUID genreId2) {
	    return new RequestBookCreationDto(
	            "Spring Boot Internals",
	            "9780132350884",
	            new BigDecimal("499.99"),
	            Map.of(
	                    UUID.randomUUID(), "John Doe"
	            ),
	            Map.of(
	                    genreId1, "Genre1",
	                    genreId2, "Genre2"
	            ),
	            true,
	            "A deep dive into Spring Boot internals and architecture",
	            BookLifeCycleStatus.ONGOING,
	            BookVisibilityStatus.PUBLIC
	    );
	}

	public static RequestCreateContentDto validBookContentRequest(UUID bookId) {
	    return new RequestCreateContentDto(
	            bookId,
	            "Getting Started with Spring Boot",
	            3,
	            new BigDecimal("29.99"),
	            "In this chapter, we explore the basics of Spring Boot and how to bootstrap a new project.",
	            true
	    );
	}
	
	public static List<RequestCreateContentDto> validMultipleContents(UUID bookId) {
	    return List.of(
	            new RequestCreateContentDto(
	                    bookId,
	                    "Chapter 4",
	                    4,
	                    new BigDecimal("0.00"),
	                    "Intro chapter content",
	                    true
	            ),
	            new RequestCreateContentDto(
	                    bookId,
	                    "Chapter 5",
	                    5,
	                    new BigDecimal("29.99"),
	                    "Advanced chapter content",
	                    false
	            )
	    );
	}
}
