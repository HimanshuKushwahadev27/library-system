package com.emi.order_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.order_service.entity.OrderStatusHistory;

public interface OrderStatusHistoryRepo extends JpaRepository<OrderStatusHistory, UUID> {

}
