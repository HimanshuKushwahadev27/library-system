package com.emi.order_service.ResponseDto;

import java.time.Instant;
import java.util.UUID;

import com.emi.order_service.enums.OrderStatus;

public record OrderHistoryDto(
		UUID id,
		UUID orderId,
		Instant changedAt,
		UUID changedBy,
		OrderStatus statusOld,
		OrderStatus statusNew	
		) {

}
