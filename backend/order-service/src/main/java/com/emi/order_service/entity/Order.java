package com.emi.order_service.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.emi.order_service.enums.OrderStatus;

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
@Table(name="orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "book_id", nullable = false, unique = false)
	private UUID bookId;
	
	@Column(name = "user_keycloak_id", nullable = false, unique = false)
	private UUID userKeycloakId;
	
	@Column(name = "amount", nullable = false, unique = false)
	private BigDecimal amount ;
	
	@Column(name = "status", nullable = false, unique = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column(name = "payment_id", nullable = false, unique = true)
	private UUID paymentId;
	
	@Column(name = "created_at", nullable = false, unique = false)
	private Instant createdAt;
	
	@Column(name = "updated_at", nullable = false, unique = false)
	private Instant updatedAt;
}
