package com.emi.order_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emi.order_service.RequestDto.RequestOrderDto;
import com.emi.order_service.ResponseDto.OrderHistoryDto;
import com.emi.order_service.ResponseDto.ResponseOrderCreationDto;
import com.emi.order_service.ResponseDto.ResponseOrderDto;
import com.emi.order_service.serviceImpl.OrderServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderServiceImpl orderService;
	
	
	@PostMapping("/create")
	public ResponseEntity<ResponseOrderCreationDto> create(
			@RequestBody @Valid RequestOrderDto request, 
			@RequestHeader("X-User-Id") String keycloakId,
			@RequestHeader("Idempotency-Key") String IdempotencyKey
			) {
		return ResponseEntity.ok(orderService.createOrder(request, UUID.fromString(keycloakId), UUID.fromString(IdempotencyKey)));
	}
	
	
	@GetMapping("/get")
	public ResponseEntity<ResponseOrderDto> getMyOrder(
			UUID orderId,
			@RequestHeader("X-User-Id") String keycloakId
			) {
		return ResponseEntity.ok(orderService.getOrder(orderId, UUID.fromString(keycloakId)));
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<ResponseOrderDto>> getMyOrders(
			@RequestHeader("X-User-Id") String keycloakId
			) {
		return ResponseEntity.ok(orderService.getMyOrders(UUID.fromString(keycloakId)));
	}
	
	@PatchMapping("/cancel")
	public void cancelMyOrder(
			UUID orderId,
			@RequestHeader("X-User-Id") String keycloakId
			) {
	 orderService.cancelOrder(orderId, UUID.fromString(keycloakId));
	}
	
	@PatchMapping("/cancel")
	public ResponseEntity<ResponseOrderDto> refundMyOrder(
			UUID orderId,
			@RequestHeader("X-User-Id") String keycloakId
			) {
		return ResponseEntity.ok(orderService.refundOrder(orderId, UUID.fromString(keycloakId)));
	}
	
	
	@GetMapping("/getHistory")
	public ResponseEntity<List<OrderHistoryDto>> getMyOrder(
			UUID orderId
			) {
		return ResponseEntity.ok(orderService.getHistory(orderId));
	}
	
	@GetMapping("/getAllOrders")
	public ResponseEntity<List<ResponseOrderDto>> getMyOrder(
			) {
		return ResponseEntity.ok(orderService.getAllOrders());
	}
	
	
	
	
	
	
}
