package com.emi.order_service.entity;


import java.time.Instant;
import java.util.UUID;

import com.emi.events.catalog.OrderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_content_access")
public class UserContentAccess {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "user_id", nullable = false, unique = false)
	private UUID userId;


	@Column(name = "access_type", nullable = false, unique = false)
	@Enumerated(EnumType.STRING)
	private OrderType accessType;

	@Column(name = "book_id", nullable = false)
	private UUID bookId;

	@Column(name = "chapter_id", nullable=true)
	private UUID chapterId;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

}
