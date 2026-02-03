package com.emi.Catalog_Service.Entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.emi.Catalog_Service.Snapshots.AuthorSnapshots;
import com.emi.Catalog_Service.Snapshots.GenreSnapshot;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="books")
public class Book {

	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="description", nullable=false, length=1000)
	private String description;
	
	@Column(name="ISBN", nullable=false, unique=true)
	private String ISBN;
	
	@Column(name="price", nullable=false)
	private BigDecimal price;
	
	@ElementCollection
	@CollectionTable(
			name="book_authors",
	        joinColumns = @JoinColumn(name = "book_id")
	  )
	private Set<AuthorSnapshots> authorSnapshots= new HashSet<>();
	
	@ElementCollection
	@CollectionTable(
			name="book_genres",
	        joinColumns = @JoinColumn(name = "book_id")
	  )
	private Set<GenreSnapshot> genreIds= new HashSet<>();
	
	private boolean isDeleted;
	
	@Column(name="free_preview", nullable=false)
	private Boolean freePreview;
	
}
