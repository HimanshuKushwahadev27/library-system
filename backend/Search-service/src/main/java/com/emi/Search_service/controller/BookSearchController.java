package com.emi.Search_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emi.Search_service.Dto.BookSearchRequestDto;
import com.emi.Search_service.document.BookDocument;
import com.emi.Search_service.service.BookSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchService searchService;

    
    @GetMapping("/search")
    public Page<BookDocument> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String lifeCycleStatus,
            @RequestParam(required = false) String visibilityStatus,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String authorNames,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedAt,desc") String[] sort
    		){
        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        
        BookSearchRequestDto request = new BookSearchRequestDto(
        		q,genre,lifeCycleStatus,visibilityStatus,minPrice,maxPrice,authorNames	
        		);
        
      return  searchService.search(request, pageable);

    }
    
    private Sort.Order parseSort(String[] sort) {
        return new Sort.Order(
                Sort.Direction.fromString(sort[1]),
                sort[0]
        );
    }
}
