package com.emi.payment_service.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.emi.events.payment.PaymentStatus;

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
@Table(name="payments")
public class Payments {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "order_id", nullable = false, unique = false)
	private UUID orderId;
	
	@Column(name = "user_keycloak_id", nullable = false, unique = false)
	private UUID userKeycloakId;	
	
	@Column(name = "gateway_transaction_id", nullable = false, unique = false)
	private String gatewayTransactionId;
	
	@Column(name = "amount", nullable = false, unique = false)
	private BigDecimal amount;
	
	@Column(name = "status", nullable = false, unique = false)
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "updated_at", nullable = false, unique = false)
	private Instant updatedAt;
	
	
}
