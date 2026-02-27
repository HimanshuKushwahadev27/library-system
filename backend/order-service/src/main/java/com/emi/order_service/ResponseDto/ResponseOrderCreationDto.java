package com.emi.order_service.ResponseDto;

import java.time.Instant;
import java.util.UUID;

public record ResponseOrderCreationDto(
		UUID orderId,
		UUID userId,
		Instant createdAt,
		String status
		
		) {

}
