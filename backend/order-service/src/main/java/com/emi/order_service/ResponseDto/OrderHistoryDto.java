package com.emi.order_service.ResponseDto;

import java.time.Instant;
import java.util.UUID;

import com.emi.order_service.enums.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO representing an order status change history entry")
public record OrderHistoryDto(

        @Schema(
            description = "Unique identifier of the order history record",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa1"
        )
        UUID id,

        @Schema(
            description = "Unique identifier of the associated order",
            example = "2fa85f64-5717-4562-b3fc-2c963f66afa2"
        )
        UUID orderId,

        @Schema(
            description = "Timestamp when the status change occurred",
            type = "string",
            format = "date-time",
            example = "2026-03-03T17:45:00Z"
        )
        Instant changedAt,

        @Schema(
            description = "Identifier of the user or system component that triggered the status change",
            example = "5fa85f64-5717-4562-b3fc-2c963f66afa3"
        )
        UUID changedBy,

        @Schema(
            description = "Previous status of the order before the change",
            example = "CREATED"
        )
        OrderStatus statusOld,

        @Schema(
            description = "New status of the order after the change",
            example = "PAID"
        )
        OrderStatus statusNew

) {}