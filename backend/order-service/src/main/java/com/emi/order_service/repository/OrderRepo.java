package com.emi.order_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.order_service.entity.Order;

public interface OrderRepo extends JpaRepository<Order, UUID> {

	boolean existsByUserKeycloakIdAndBookId(UUID userId, UUID bookid);

	List<Order> findAllByUserKeycloakId(UUID userKeycloakId);

}
