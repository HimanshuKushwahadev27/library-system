package com.emi.order_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.events.catalog.OrderType;
import com.emi.order_service.entity.UserContentAccess;

public interface UserContentAccessRepo extends JpaRepository<UserContentAccess, UUID> {

	boolean existsByUserIdAndBookIdAndAccessType(UUID userId, UUID bookid, OrderType type);

	boolean existsByUserIdAndChapterId(UUID userId, UUID bookid);

}
