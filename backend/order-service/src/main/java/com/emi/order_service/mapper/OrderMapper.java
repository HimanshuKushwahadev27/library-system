package com.emi.order_service.mapper;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.emi.order_service.RequestDto.RequestOrderDto;
import com.emi.order_service.ResponseDto.ResponseOrderCreationDto;
import com.emi.order_service.ResponseDto.ResponseOrderDto;
import com.emi.order_service.entity.Order;
import com.emi.order_service.enums.OrderStatus;

@Component
public class OrderMapper {

	public Order getEntity(RequestOrderDto request, UUID keycloakId) {
		Order order = new Order();
		
		order.setAmount(request.price());
		order.setBookId(request.bookid());
		order.setCreatedAt(Instant.now());
		order.setStatus(OrderStatus.CREATED);
		order.setUserKeycloakId(keycloakId);
		order.setUpdatedAt(Instant.now());
		
		return order;
	}

	public ResponseOrderDto getResponse(Order order) {
		return new ResponseOrderDto(
				order.getId(),
				order.getCreatedAt(),
				order.getStatus().toString(),
				order.getAmount(),
				order.getUserKeycloakId(),
				order.getBookId()
				);
	}

	public ResponseOrderCreationDto toResponseCreation(Order order) {
		return new ResponseOrderCreationDto(
				order.getId(),
				order.getUserKeycloakId(),
				order.getCreatedAt(),
				order.getStatus().toString()
				)
				
				;
	}

}
