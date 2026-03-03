package com.emi.payment_service.ResponseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.emi.payment_service.enums.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;



@Schema(description = "Response DTO representing order details")
public record ResponseOrderDto(

        @Schema(
            description = "Unique identifier of the user who placed the order",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa1"
        )
        UUID userId,

        @Schema(
            description = "Unique identifier of the order",
            example = "2fa85f64-5717-4562-b3fc-2c963f66afa2"
        )
        UUID orderId,

        @Schema(
            description = "Total amount of the order",
            example = "999.99"
        )
        BigDecimal totalAmount,

        @Schema(
            description = "Current status of the order",
            example = "CREATED"
        )
        OrderStatus status,

        @Schema(
            description = "Timestamp when the order was created",
            type = "string",
            format = "date-time",
            example = "2026-03-03T17:10:00Z"
        )
        Instant createdAt

) {}