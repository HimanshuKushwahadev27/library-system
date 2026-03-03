package com.emi.payment_service.ResponseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.emi.events.payment.PaymentStatus;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Response DTO representing payment details in the system")
public record ResponsePaymentDto(

        @Schema(
            description = "Unique identifier of the payment in the system",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        )
        UUID paymentId,

        @Schema(
            description = "Unique identifier of the associated order",
            example = "2fa85f64-5717-4562-b3fc-2c963f66afa2"
        )
        UUID orderId,

        @Schema(
            description = "Payment amount",
            example = "499.99"
        )
        BigDecimal amount,

        @Schema(
            description = "Current status of the payment",
            example = "SUCCESS"
        )
        PaymentStatus status,

        @Schema(
            description = "Transaction ID returned by the payment gateway (e.g., Stripe)",
            example = "pi_3Nk2aL2eZvKYlo2C1abcd123"
        )
        String gatewayTransactionId,

        @Schema(
            description = "Timestamp when the payment was created",
            type = "string",
            format = "date-time",
            example = "2026-03-03T16:30:00Z"
        )
        Instant createdAt

) {}