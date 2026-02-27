package com.emi.order_service.serviceImpl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.emi.order_service.RequestDto.RequestOrderDto;
import com.emi.order_service.ResponseDto.OrderHistoryDto;
import com.emi.order_service.ResponseDto.ResponseOrderCreationDto;
import com.emi.order_service.ResponseDto.ResponseOrderDto;
import com.emi.order_service.entity.IdempotencyRecord;
import com.emi.order_service.entity.Order;
import com.emi.order_service.entity.OrderStatusHistory;
import com.emi.order_service.enums.IdempotencyStatus;
import com.emi.order_service.enums.OrderStatus;
import com.emi.order_service.exceptions.OrderExistsException;
import com.emi.order_service.exceptions.UnauthorizedAccessException;
import com.emi.order_service.mapper.IdempotencyMapper;
import com.emi.order_service.mapper.OrderMapper;
import com.emi.order_service.mapper.OrderStatusHistoryMapper;
import com.emi.order_service.repository.IdempotencyRepo;
import com.emi.order_service.repository.OrderRepo;
import com.emi.order_service.repository.OrderStatusHistoryRepo;
import com.emi.order_service.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private static final UUID SYSTEM_USER_ID =
	        UUID.fromString("00000000-0000-0000-0000-000000000000");
	private final IdempotencyRepo idempotencyRepo;
	private final OrderStatusHistoryRepo orderStatusHistoryRepo;
	private final OrderRepo orderRepo;
	private final OrderMapper orderMapper;
	private final IdempotencyMapper idempotencyMapper;
	private final ObjectMapper objectMapper;
	private final OrderStatusHistoryMapper orderHistoryMapper;

	@Transactional
	@Override
	public ResponseOrderCreationDto createOrder(RequestOrderDto request, UUID idempotencyId, UUID keycloakId) {

		if (orderRepo.existsByUserKeycloakIdAndBookId(keycloakId, request.bookid())) {
			throw new OrderExistsException("order for the user with id " + keycloakId + "for the book with id "
					+ request.bookid() + "already exists");
		}

		IdempotencyRecord optional =
		        idempotencyMapper.getEntity(request, idempotencyId, keycloakId);

		try {
		    idempotencyRepo.save(optional);
		} catch (DataIntegrityViolationException ex) {
			
			//already present
		    IdempotencyRecord existing =
		            idempotencyRepo
		                .findByKeycloakIdAndIdempotencyKey(keycloakId, idempotencyId)
		                .orElseThrow();

		    if (existing.getStatus() == IdempotencyStatus.COMPLETED) {
		        try {
		            return objectMapper.readValue(
		                    existing.getResponseBody(),
		                    ResponseOrderCreationDto.class
		            );
		        } catch (JsonProcessingException e) {
		            throw new RuntimeException("Failed to deserialize JSON", e);
		        }
		    }

		    throw new IllegalStateException("Request already in progress");
		}

		IdempotencyRecord idempotency = idempotencyMapper
				.getEntity(
						request,
						idempotencyId,
						keycloakId
						);

		idempotencyRepo.save(idempotency);

		Order order = orderMapper.getEntity(request, keycloakId);
		orderRepo.save(order);
		OrderStatusHistory history = orderHistoryMapper.getEntity(order);
		orderStatusHistoryRepo.save(history);

		ResponseOrderCreationDto response = orderMapper.toResponseCreation(order);

		// payment call

		idempotency = idempotencyMapper.updateIdemp(idempotency, response);

		return orderMapper.toResponseCreation(order);
	}

	@Override
	public ResponseOrderDto getOrder(UUID orderId, UUID keycloakId) {

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderExistsException("no order for the given id +" + orderId));

		if (!order.getUserKeycloakId().equals(keycloakId)) {
			throw new UnauthorizedAccessException("U cant access this order");
		}
		return orderMapper.getResponse(order);
	}

	@Override
	public List<ResponseOrderDto> getMyOrders(UUID userKeycloakId) {

		List<Order> orders = orderRepo.findAllByUserKeycloakId(userKeycloakId);

		return orders.stream().map(orderMapper::getResponse).toList();
	}

	@Transactional
	@Override
	public void cancelOrder(UUID orderId, UUID userKeycloakId) {
		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderExistsException("no order for the given id +" + orderId));

		if (!order.getUserKeycloakId().equals(userKeycloakId)) {
			throw new UnauthorizedAccessException("U cant access this order");
		}

		OrderStatusHistory history = orderHistoryMapper.updateHistoryCancel(order.getStatus(), order.getId(),
				userKeycloakId);
		orderStatusHistoryRepo.save(history);
		this.validation(order.getStatus());
		
		if (order.getStatus() == OrderStatus.PAID) {
			// call payment refund api
		}

		order.setStatus(OrderStatus.CANCELLED);
		order.setUpdatedAt(Instant.now());
		orderRepo.save(order);
	}

	@Override
	public void markPaid(UUID orderId) {

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderExistsException("no order for the given id +" + orderId));

		OrderStatusHistory history = orderHistoryMapper.updateHistoryPaid(order.getStatus(), order.getId(),
				order.getUserKeycloakId());
		orderStatusHistoryRepo.save(history);

		order.setStatus(OrderStatus.PAID);
		order.setUpdatedAt(Instant.now());
		orderRepo.save(order);
	}

	@Override
	public void markFailed(UUID orderId) {
		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderExistsException("no order for the given id +" + orderId));
		
		if(order.getStatus() == OrderStatus.FAILED) {
			throw new IllegalStateException("Order is not completed");
		}
		
		order.setStatus(OrderStatus.FAILED);
		order.setUpdatedAt(Instant.now());
		orderRepo.save(order);
		
		OrderStatusHistory history = orderHistoryMapper.updateHistoryfailed(order.getStatus(), order.getId(),
				SYSTEM_USER_ID);
		orderStatusHistoryRepo.save(history);
		
		
	}

	@Override
	public ResponseOrderDto refundOrder(UUID orderId, UUID keycloakId) {

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderExistsException("no order for the given id +" + orderId));

		if (!order.getUserKeycloakId().equals(keycloakId)) {
			throw new UnauthorizedAccessException("U cant access this order");
		}

		OrderStatusHistory history = orderHistoryMapper.updateHistoryRefund(order.getStatus(), order.getId(),
				order.getUserKeycloakId());
		orderStatusHistoryRepo.save(history);
		
		this.validation(order.getStatus());
		
		order.setStatus(OrderStatus.REFUNDED);
		order.setUpdatedAt(Instant.now());
		orderRepo.save(order);
		// call refund (payment api)

		return orderMapper.getResponse(order);

	}

	@Override
	public List<OrderHistoryDto> getHistory(UUID orderId) {

		List<OrderStatusHistory> histories = orderStatusHistoryRepo.findAllByOrderId(orderId);
		return histories.stream().map(orderHistoryMapper::toDto).toList();
	}

	@Override
	public List<ResponseOrderDto> getAllOrders() {
		List<Order> order = orderRepo.findAll();

		return order.stream().map(orderMapper::getResponse).toList();
	}
	
	public void validation(OrderStatus status) {
		if(status == OrderStatus.CANCELLED) {
			throw new IllegalStateException("Order Already canceled");
		}
		
		if(status == OrderStatus.FAILED) {
			throw new IllegalStateException("Order is not completed");
		}
		
		if(status == OrderStatus.REFUNDED) {
			throw new IllegalStateException("Order is already refunded");
		}
	}

}
