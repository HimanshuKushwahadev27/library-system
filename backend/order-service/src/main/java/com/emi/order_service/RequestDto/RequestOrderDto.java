package com.emi.order_service.RequestDto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record RequestOrderDto(
		
	    @NotEmpty(message = "Order must contain at least one item")
        @Valid
        List<OrderItemRequestDto> items
		) {

}
