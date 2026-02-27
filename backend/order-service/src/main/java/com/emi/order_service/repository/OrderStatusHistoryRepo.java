package com.emi.order_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.order_service.entity.OrderStatusHistory;

public interface OrderStatusHistoryRepo extends JpaRepository<OrderStatusHistory, UUID> {

	OrderStatusHistory findByOrderId(UUID orderId);

	List<OrderStatusHistory> findAllByOrderId(UUID orderId);

}
