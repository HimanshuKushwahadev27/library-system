package com.emi.order_service.serviceImpl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.emi.events.catalog.ContentPurchasedEvent;
import com.emi.events.catalog.OrderType;
import com.emi.events.mail.MailEvent;
import com.emi.order_service.RequestDto.OrderItemRequestDto;
import com.emi.order_service.RequestDto.RequestOrderDto;
import com.emi.order_service.ResponseDto.OrderHistoryDto;
import com.emi.order_service.ResponseDto.ResponseOrderDto;
import com.emi.order_service.ResponseDto.ResponseOrderToPaymentDto;
import com.emi.order_service.entity.IdempotencyRecord;
import com.emi.order_service.entity.Order;
import com.emi.order_service.entity.OrderItem;
import com.emi.order_service.entity.OrderStatusHistory;
import com.emi.order_service.entity.UserContentAccess;
import com.emi.order_service.enums.IdempotencyStatus;
import com.emi.order_service.enums.OrderStatus;
import com.emi.order_service.exceptions.OrderExistsException;
import com.emi.order_service.exceptions.UnauthorizedAccessException;
import com.emi.order_service.kafkaEvent.ProduceCatalogEvent;
import com.emi.order_service.kafkaEvent.ProduceMailEvent;
import com.emi.order_service.mapper.CatalogMapper;
import com.emi.order_service.mapper.IdempotencyMapper;
import com.emi.order_service.mapper.MailMapper;
import com.emi.order_service.mapper.OrderItemMapper;
import com.emi.order_service.mapper.OrderMapper;
import com.emi.order_service.mapper.OrderStatusHistoryMapper;
import com.emi.order_service.mapper.UserContentAccessMapper;
import com.emi.order_service.repository.IdempotencyRepo;
import com.emi.order_service.repository.OrderItemRepo;
import com.emi.order_service.repository.OrderRepo;
import com.emi.order_service.repository.OrderStatusHistoryRepo;
import com.emi.order_service.repository.UserContentAccessRepo;
import com.emi.order_service.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private final CatalogMapper catalogMapper;
	private final ProduceCatalogEvent produceCatalogEvent;
	private final CatalogService catalogService;
	private final ProduceMailEvent mailEvent;
	private final MailMapper mailMapper;
	private static final UUID SYSTEM_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
	private final IdempotencyRepo idempotencyRepo;
	private final OrderStatusHistoryRepo orderStatusHistoryRepo;
	private final OrderRepo orderRepo;
	private final OrderMapper orderMapper;
	private final IdempotencyMapper idempotencyMapper;
	private final ObjectMapper objectMapper;
	private final OrderStatusHistoryMapper orderHistoryMapper;
	private final OrderItemMapper orderItemMapper;
	private final OrderItemRepo orderItemRepo;
	private final UserContentAccessRepo userContentRepo;
	private final UserContentAccessMapper userContentMapper;

	@Transactional
	@Override
	public ResponseOrderDto createOrder(
			RequestOrderDto request, 
			UUID idempotencyId,
			UUID keycloakId,
			String  email, 
			String firstName,
			String lastName) {

		validateOwnership(keycloakId, request.items());

		IdempotencyRecord idempotency = idempotencyMapper.getEntity(request, idempotencyId, keycloakId);

		try {
			idempotencyRepo.save(idempotency);
		} catch (DataIntegrityViolationException ex) {

			// already present
			IdempotencyRecord existing = idempotencyRepo.findByKeycloakIdAndIdempotencyKey(keycloakId, idempotencyId)
					.orElseThrow();

			if (existing.getStatus() == IdempotencyStatus.COMPLETED) {
				try {
					return objectMapper.readValue(existing.getResponseBody(), ResponseOrderDto.class);
				} catch (JsonProcessingException e) {
					throw new RuntimeException("Failed to deserialize JSON", e);
				}
			}

			throw new IllegalStateException("Request already in progress");
		}
		
		
		this.validationOfRequest(request);
		
		Order order = orderMapper.getEntity(keycloakId, email, firstName, lastName);
		orderRepo.save(order);
		
		List<OrderItem> items = orderItemMapper.getEntities(request,catalogService, order.getId());
		items.stream().forEach(t -> orderItemRepo.save(t));

		BigDecimal totalPrice = orderItemMapper.getTotalPrice(items);
		order.setAmount(totalPrice);

		OrderStatusHistory history = orderHistoryMapper.getEntity(order);
		orderStatusHistoryRepo.save(history);

		ResponseOrderDto response = orderMapper.getResponse(order, items);

		// payment call
		idempotency = idempotencyMapper.updateIdemp(idempotency, response);
		idempotencyRepo.save(idempotency);

		return response;
	}

	private void validationOfRequest(RequestOrderDto request) {
		boolean containsBook = request.items().stream()
		        .anyMatch(i -> i.type() == OrderType.BOOK);

		boolean containsChapter = request.items().stream()
		        .anyMatch(i -> i.type() == OrderType.CONTENT);

		if (containsBook && containsChapter) {
		    throw new IllegalStateException("Cannot purchase full book and chapters together");
		}		
	}

	@Override
	public ResponseOrderDto getOrder(UUID orderId, UUID keycloakId) {

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderExistsException("no order for the given id +" + orderId));

		if (!order.getUserKeycloakId().equals(keycloakId)) {
			throw new UnauthorizedAccessException("U cant access this order");
		}

		List<OrderItem> items = orderItemRepo.findAllByOrderId(order.getId());
		return orderMapper.getResponse(order, items);
	}

	@Override
	public List<ResponseOrderDto> getMyOrders(UUID userKeycloakId) {

		List<Order> orders = orderRepo.findAllByUserKeycloakId(userKeycloakId);

		return orders.stream().map(o -> this.getOrder(o.getId(), userKeycloakId)).toList();
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
			throw new IllegalStateException("U can't cancel the paid order");
		}

		order.setStatus(OrderStatus.CANCELLED);
		order.setUpdatedAt(Instant.now());
		orderRepo.save(order);
	}
	

	@Transactional
	@Override
	public void markPaid(UUID orderId, UUID paymentId) {

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderExistsException("no order for the given id +" + orderId));

		OrderStatusHistory history = orderHistoryMapper.updateHistoryPaid(order.getStatus(), order.getId(),
				order.getUserKeycloakId());
		orderStatusHistoryRepo.save(history);

		order.setStatus(OrderStatus.PAID);
		order.setUpdatedAt(Instant.now());
		order.setPaymentId(paymentId);
		orderRepo.save(order);

		List<OrderItem> items = orderItemRepo.findAllByOrderId(order.getId());

		for (OrderItem item : items) {
			UserContentAccess contentAccess = userContentMapper.getEntity(order, item);
			userContentRepo.save(contentAccess);
		}
		
		MailEvent eventMail = mailMapper.getEvent(order, items.getFirst());

		ContentPurchasedEvent event = catalogMapper.getEvent(order, items);
		
		produceCatalogEvent.orderSuccess(event);
		mailEvent.sendEventMail(eventMail);
	}

	@Transactional
	@Override
	public void markFailed(UUID orderId, UUID paymentId) {
		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderExistsException("no order for the given id +" + orderId));

		if (order.getStatus() == OrderStatus.FAILED) {
			throw new IllegalStateException("Order is not completed");
		}

		if (order.getStatus() != OrderStatus.PAYMENT_PENDING) {
			throw new IllegalStateException("Invalid state transition");
		}

		order.setStatus(OrderStatus.FAILED);
		order.setPaymentId(paymentId);
		order.setUpdatedAt(Instant.now());
		orderRepo.save(order);

		OrderStatusHistory history = orderHistoryMapper.updateHistoryfailed(order.getStatus(), order.getId(),
				SYSTEM_USER_ID);
		orderStatusHistoryRepo.save(history);

	}

	@Override
	public List<OrderHistoryDto> getHistory(UUID orderId) {

		List<OrderStatusHistory> histories = orderStatusHistoryRepo.findAllByOrderId(orderId);
		return histories.stream().map(orderHistoryMapper::toDto).toList();
	}

	@Override
	public List<ResponseOrderDto> getAllOrders() {
		List<Order> order = orderRepo.findAll();

		return order.stream().map(o -> {
			List<OrderItem> items = orderItemRepo.findAllByOrderId(o.getId());

			return orderMapper.getResponse(o, items);

		}).toList();
	}

	public void validation(OrderStatus status) {
		if (status == OrderStatus.CANCELLED) {
			throw new IllegalStateException("Order Already canceled");
		}

		if (status == OrderStatus.FAILED) {
			throw new IllegalStateException("Order is not completed");
		}
	}

	public void validateOwnership(UUID userId, List<OrderItemRequestDto> items) {

		for (OrderItemRequestDto item : items) {

			if (item.type() == OrderType.BOOK) {

				boolean ownBook = userContentRepo.existsByUserIdAndBookIdAndAccessType(userId, item.bookId(),
						item.type());

				if (ownBook) {
					throw new IllegalStateException("Book already purchased");
				}

			} else if (item.type() == OrderType.CONTENT) {

				boolean ownBook = userContentRepo.existsByUserIdAndBookIdAndAccessType(userId, item.bookId(),
						OrderType.BOOK);

				if (ownBook) {
					throw new IllegalStateException("Book already purchased");
				}

				boolean ownChapter = userContentRepo.existsByUserIdAndChapterId(userId, item.chapterId());

				if (ownChapter) {
					throw new IllegalStateException("Chapter already purchased");
				}
			}
		}
	}

	@Override
	public ResponseOrderToPaymentDto orderValidation(UUID orderId) {
		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderExistsException("no order for the given id +" + orderId));

		return orderMapper.getResponseToPayment(order);
	}
	
	
	

}
