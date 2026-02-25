package com.emi.Search_service.Repo;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.emi.Search_service.document.BookDocument;

public interface BookDocumentRepo extends ElasticsearchRepository<BookDocument, String> {

	Optional<BookDocument> findByBookId(String bookId);

}
