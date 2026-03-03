package com.emi.payment_service.RequestDto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request DTO to initiate payment for a specific order")
public record RequestPaymentDto(

        @NotNull(message = "Order ID is required")
        @Schema(
            description = "Unique identifier of the order for which payment is being initiated",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        )
        UUID orderId

) {}