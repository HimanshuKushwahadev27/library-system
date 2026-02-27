package com.emi.order_service.ResponseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ResponseOrderDto(
		UUID orderid,
		Instant createdAt,
		String status,
		BigDecimal price,
		UUID userKeycloakId,
		UUID bookId
		) {

}
