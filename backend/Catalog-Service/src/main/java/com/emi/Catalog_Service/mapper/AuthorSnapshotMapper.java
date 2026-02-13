package com.emi.Catalog_Service.mapper;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.emi.Catalog_Service.Entity.Book;
import com.emi.Catalog_Service.Snapshots.AuthorSnapshots;

@Component
public class AuthorSnapshotMapper {

	public AuthorSnapshots giveSnapshotFromAuthor(UUID id, String name) {
		return AuthorSnapshots.builder()
				.id(id)
				.name(name)
				.build();
	}
	
	public void setAuthorSnapshot(Book book,Map<UUID,String> authorInfo ) {
		book.setAuthorSnapshots(authorInfo
				.entrySet()
				.stream()
				.map(entry -> giveSnapshotFromAuthor
						(entry.getKey(), entry.getValue())
						)
				.collect(Collectors.toSet()));
	}
}
