package com.emi.order_service.ResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

import com.emi.events.catalog.OrderType;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Response DTO representing a single item within an order")
public record ResponseOrderItemDto(

        @Schema(
            description = "Type of item purchased (BOOK or CHAPTER)",
            example = "BOOK"
        )
        OrderType itemType,

        @Schema(
            description = "Unique identifier of the book associated with this item",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa1",
            nullable = true
        )
        UUID bookId,

        @Schema(
            description = "Unique identifier of the chapter (only applicable if itemType = CHAPTER)",
            example = "2fa85f64-5717-4562-b3fc-2c963f66afa2",
            nullable = true
        )
        UUID chapterId,

        @Schema(
            description = "Price of this individual item",
            example = "199.99"
        )
        BigDecimal price

) {}