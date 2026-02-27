package com.emi.order_service.service;

import java.util.List;
import java.util.UUID;

import com.emi.order_service.RequestDto.RequestOrderDto;
import com.emi.order_service.ResponseDto.OrderHistoryDto;
import com.emi.order_service.ResponseDto.ResponseOrderCreationDto;
import com.emi.order_service.ResponseDto.ResponseOrderDto;

public interface OrderService {
	
	public ResponseOrderCreationDto createOrder(
			RequestOrderDto request,
			UUID idempId,
			UUID keycloakId);
	public ResponseOrderDto getOrder(
			UUID orderId,
			UUID keycloakId);
	public List<ResponseOrderDto> getMyOrders(UUID userKeycloakId);
	public void cancelOrder(
			UUID orderId,
			UUID userKeycloakId);
	public void markPaid(UUID orderId);
	public void markFailed(UUID orderId);
	public ResponseOrderDto refundOrder(UUID orderId, UUID userKeycloakId);
	public List<OrderHistoryDto> getHistory(UUID orderId);
	public List<ResponseOrderDto> getAllOrders();
}
