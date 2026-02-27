package com.emi.order_service.mapper;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.emi.order_service.ResponseDto.OrderHistoryDto;
import com.emi.order_service.entity.Order;
import com.emi.order_service.entity.OrderStatusHistory;
import com.emi.order_service.enums.OrderStatus;

@Component
public class OrderStatusHistoryMapper {

	public OrderStatusHistory getEntity(Order order) {
		OrderStatusHistory history = new OrderStatusHistory();
		
		history.setChangedAt(Instant.now());
		history.setChangedBy(order.getUserKeycloakId());
		history.setNewStatus(OrderStatus.CREATED);
		history.setOldStatus(OrderStatus.CREATED);
		history.setOrderId(order.getId());
		
		return history;
	}

	public OrderStatusHistory updateHistoryCancel(OrderStatus oldStatus, UUID keycloakId, UUID orderId) {
		OrderStatusHistory history = new OrderStatusHistory();
		
		history.setChangedAt(Instant.now());
		history.setChangedBy(keycloakId);
		history.setNewStatus(OrderStatus.CANCELLED);
		history.setOldStatus(oldStatus);
		history.setOrderId(orderId);
		
		return history;

	}

	public OrderStatusHistory updateHistoryPaid(OrderStatus status, UUID id, UUID userKeycloakId) {
		OrderStatusHistory history = new OrderStatusHistory();
		
		history.setChangedAt(Instant.now());
		history.setChangedBy(userKeycloakId);
		history.setNewStatus(OrderStatus.PAID);
		history.setOldStatus(status);
		history.setOrderId(id);
		
		return history;
	}

	public OrderStatusHistory updateHistoryRefund(OrderStatus status, UUID id, UUID userKeycloakId) {
		OrderStatusHistory history = new OrderStatusHistory();
		
		history.setChangedAt(Instant.now());
		history.setChangedBy(userKeycloakId);
		history.setNewStatus(OrderStatus.REFUNDED);
		history.setOldStatus(status);
		history.setOrderId(id);
		
		return history;
	}
	
	public OrderHistoryDto toDto(OrderStatusHistory history){
		return new OrderHistoryDto(
				history.getId(),
				history.getOrderId(),
				history.getChangedAt(),
				history.getChangedBy(),
				history.getOldStatus(),
				history.getNewStatus()
				);
	}

	public OrderStatusHistory updateHistoryfailed(OrderStatus status, UUID id, UUID userKeycloakId) {
		OrderStatusHistory history = new OrderStatusHistory();
		
		history.setChangedAt(Instant.now());
		history.setChangedBy(userKeycloakId);
		history.setNewStatus(OrderStatus.FAILED);
		history.setOldStatus(status);
		history.setOrderId(id);
		
		return history;	}

}
