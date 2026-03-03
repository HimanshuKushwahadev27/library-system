package com.emi.payment_service.ResponseDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO representing a refund returned from Stripe")
public record StripeRefundResponseDto(

        @Schema(
            description = "Unique identifier of the refund in Stripe",
            example = "re_3Nk2bL2eZvKYlo2C1abcd123"
        )
        String id,

        @Schema(
            description = "Current status of the refund",
            example = "succeeded"
        )
        String status,

        @JsonProperty("payment_intent")
        @Schema(
            description = "ID of the PaymentIntent associated with this refund",
            example = "pi_3Nk2aL2eZvKYlo2C1abcd123"
        )
        String paymentIntent,

        @Schema(
            description = "Refund amount in the smallest currency unit (e.g., paise for INR, cents for USD)",
            example = "49900"
        )
        Long amount

) {}