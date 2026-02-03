package com.emi.Catalog_Service.Entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="book_contents")
public class Book_Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	
	@Column(name="book_id", nullable=false)
	private UUID book_id;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="chapterNumber", nullable=false)
	private Integer chapterNumber;
	
	@Column(name="price", nullable=false)
	private BigDecimal price;
	
	@Lob
	@Column(name="content", nullable=false, columnDefinition="TEXT")
	private String content;
	
	@Column(name="free_preview", nullable=false)
	private Boolean freePreview;	
}
