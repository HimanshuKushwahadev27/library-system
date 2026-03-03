package com.emi.order_service.entity;

import java.math.BigDecimal;
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
@Table(name = "order_item")
public class OrderItem {

	public OrderItem(UUID orderId, BigDecimal price, OrderType type, UUID bookId, UUID chapterId, Instant createdAt,
			Instant updatedAt) {
		this.orderId = orderId;
		this.amount = price;
		this.itemType = type;
		this.bookId = bookId;
		this.chapterId = chapterId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "order_id", nullable = false, unique = false)
	private UUID orderId;

	@Column(name = "amount", nullable = false, unique = false)
	private BigDecimal amount;

	@Column(name = "item_type", nullable = false, unique = false)
	@Enumerated(EnumType.STRING)
	private OrderType itemType;

	@Column(name = "book_id", nullable = false)
	private UUID bookId;

	@Column(name = "chapter_id", nullable=true)
	private UUID chapterId;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false, unique = false)
	private Instant updatedAt;
}
