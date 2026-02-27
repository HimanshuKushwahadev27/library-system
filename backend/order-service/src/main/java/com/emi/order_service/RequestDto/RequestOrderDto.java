package com.emi.order_service.RequestDto;

import java.math.BigDecimal;
import java.util.UUID;

public record RequestOrderDto(
		UUID bookid,
		BigDecimal price
		) {

}
