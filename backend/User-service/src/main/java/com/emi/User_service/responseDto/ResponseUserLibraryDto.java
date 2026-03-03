package com.emi.User_service.responseDto;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Response DTO representing a user's purchased book entry in the library")
public record ResponseUserLibraryDto(

        @Schema(
            description = "Unique identifier of the user library entry",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        )
        UUID id,

        @Schema(
            description = "Unique identifier of the purchased book",
            example = "2fa85f64-5717-4562-b3fc-2c963f66afa7"
        )
        UUID bookId,

        @Schema(
            description = "Timestamp when the book was purchased",
            example = "2026-03-03T10:15:30Z"
        )
        Instant purchasedAt
) {}