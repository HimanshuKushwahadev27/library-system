package com.emi.Authoring_service.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.Authoring_service.entity.AuthorDraftBook;

public interface DraftBookRepo extends JpaRepository<AuthorDraftBook, UUID> {

	boolean existsByIsbn(String Isbn);

	Optional<List<AuthorDraftBook>> findAllByAuthorId(UUID authorId);

	Optional<AuthorDraftBook> findByAuthorIdAndId(UUID authorId, UUID draftBookId);

}
