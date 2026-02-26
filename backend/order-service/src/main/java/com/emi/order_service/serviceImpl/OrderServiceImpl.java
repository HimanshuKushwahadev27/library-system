package com.emi.order_service.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.emi.order_service.RequestDto.RequestOrderDto;
import com.emi.order_service.ResponseDto.ResponseOrderCreationDto;
import com.emi.order_service.ResponseDto.ResponseOrderDto;
import com.emi.order_service.entity.OrderStatusHistory;
import com.emi.order_service.exceptions.OrderExistsException;
import com.emi.order_service.repository.IdempotencyRepo;
import com.emi.order_service.repository.OrderRepo;
import com.emi.order_service.repository.OrderStatusHistoryRepo;
import com.emi.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final IdempotencyRepo idempotencyRepo;
	private final OrderStatusHistoryRepo orderStatusHistoryRepo;
	private final OrderRepo orderRepo;
	
	@Override
	public ResponseOrderCreationDto createOrder(RequestOrderDto request) {
		if(orderRepo.existsByUserKeycloakIdAndBookId(request.userId(), request.bookid())) {
			throw new OrderExistsException("order for the user with id " +request.userId() + "for the book with id " +request.bookid() + "already exists");
		}
	}

	@Override
	public ResponseOrderDto getOrder(UUID orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResponseOrderDto> getMyOrders(UUID userKeycloakId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String calcelOrder(UUID orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markPaid(UUID orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void markFailed(UUID orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String refundOrder(UUID orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderStatusHistory getHistory(UUID orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResponseOrderDto> getAllOrders(UUID userKeycloakId) {
		// TODO Auto-generated method stub
		return null;
	}

}
