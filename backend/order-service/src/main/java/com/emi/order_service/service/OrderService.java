package com.emi.order_service.service;

import java.util.List;
import java.util.UUID;

import com.emi.order_service.RequestDto.RequestOrderDto;
import com.emi.order_service.ResponseDto.ResponseOrderCreationDto;
import com.emi.order_service.ResponseDto.ResponseOrderDto;
import com.emi.order_service.entity.OrderStatusHistory;

public interface OrderService {
	
	public ResponseOrderCreationDto createOrder(RequestOrderDto request, UUID idempId);
	public ResponseOrderDto getOrder(UUID orderId);
	public List<ResponseOrderDto> getMyOrders(UUID userKeycloakId);
	public String calcelOrder(UUID orderId);
	public void markPaid(UUID orderId);
	public void markFailed(UUID orderId);
	public String refundOrder(UUID orderId);
	public OrderStatusHistory getHistory(UUID orderId);
	public List<ResponseOrderDto> getAllOrders(UUID userKeycloakId);
}
