package com.emi.Search_service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import com.emi.Search_service.Dto.BookSearchRequestDto;
import com.emi.Search_service.document.BookDocument;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookSearchService {
	
    private final ElasticsearchOperations elasticsearchOperations;
    
    public Page<BookDocument> search(BookSearchRequestDto request, Pageable pageable) {
		
		BoolQuery.Builder boolQuery = new BoolQuery.Builder();
		
		//full text search 
		if(request.query() != null && !request.query().isBlank()) {
			
			boolQuery.must(m -> m
					.multiMatch(mm -> mm
							.query(request.query())
							.fields(     
									"title^3",        
				                    "authorName^2",  
				                    "description")
							)
					);
		}
		
		//author filter
		if(request.authorNames()!=null) {
			
			boolQuery.filter(f -> f
					.term(t -> t
							.field("authorName")
							.value(request.authorNames())
							)
					);
		}
		
		//genre filter
		if(request.genreName()!=null) {
			
			boolQuery.filter(f -> f
					.term(t -> t
							.field("genreName")
							.value(request.genreName())
							)
					);
			
		}
		
	    // Lifecycle filter
	    if (request.lifeCycleStatus() != null) {
	        boolQuery.filter(f -> f
	            .term(t -> t
	                .field("lifeCycleStatus")
	                .value(request.lifeCycleStatus())
	            )
	        );
	    }
	    
	    // visibilityStatus filter
	    if (request.visibilityStatus() != null) {
	        boolQuery.filter(f -> f
	            .term(t -> t
	                .field("visibilityStatus")
	                .value(request.visibilityStatus())
	            )
	        );
	    }
	    
	    // Price range
	    if (request.minPrice() != null || request.maxPrice() != null) {
	        boolQuery.filter(f -> f
	        		.range(r -> r
	        			    .number(n -> n
	        			        .field("price")
	        			        .gte(request.minPrice())
	        			        .lte(request.maxPrice())
	        			    )
	        			)
	        );
	    }
		
	    NativeQuery query = NativeQuery.builder()
	    		.withQuery(boolQuery.build()._toQuery())
	    		.withPageable(pageable)
	    		.build()
	    		;
	    
	    SearchHits<BookDocument> searchHits = elasticsearchOperations
	    		.search(query, BookDocument.class);
	    
	    List<BookDocument> content = searchHits.stream()
	            .map(SearchHit::getContent)
	    		.toList()
	    		;
	    
	    return new PageImpl<>(content, pageable, searchHits.getTotalHits());

	}

}
