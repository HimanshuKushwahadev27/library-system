package com.emi.order_service.entity;

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
@Table(name="order_status_history")
public class OrderStatusHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "order_id", nullable = false, unique = false)
	private UUID orderId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "old_Status", nullable = false, unique = false)
	private OrderStatus oldStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "new_Status", nullable = false, unique = false)
	private OrderStatus newStatus;
	
	@Column(name = "changed_at", nullable = false, unique = false)
	private Instant changedAt;	
	
	
	@Column(name = "changed_by", nullable = false, unique = false)
	private UUID changedBy;
}
