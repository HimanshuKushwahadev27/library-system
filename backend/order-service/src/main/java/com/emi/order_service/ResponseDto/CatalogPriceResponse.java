package com.emi.order_service.ResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO containing pricing details for selected book chapters")
public record CatalogPriceResponse(

        @Schema(
            description = "Unique identifier of the book",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa1"
        )
        UUID bookId,

        @Schema(
            description = "List of chapter IDs included in the price calculation",
            example = "[\"2fa85f64-5717-4562-b3fc-2c963f66afa2\", \"7fa85f64-5717-4562-b3fc-2c963f66afa3\"]"
        )
        List<UUID> chapterId,

        @Schema(
            description = "Total calculated price for the selected chapters",
            example = "299.99"
        )
        BigDecimal price

) {}