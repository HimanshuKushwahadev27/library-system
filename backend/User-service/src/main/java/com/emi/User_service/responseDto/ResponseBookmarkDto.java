package com.emi.User_service.responseDto;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO representing a user's bookmarked chapter in a book")
public record ResponseBookmarkDto(

        @Schema(
            description = "Unique identifier of the bookmark",
            example = "5fa85f64-5717-4562-b3fc-2c963f66afa1"
        )
        UUID id,

        @Schema(
            description = "Unique identifier of the book",
            example = "6fa85f64-5717-4562-b3fc-2c963f66afa2"
        )
        UUID bookId,

        @Schema(
            description = "Unique identifier of the bookmarked chapter",
            example = "7fa85f64-5717-4562-b3fc-2c963f66afa3"
        )
        UUID chapterID,

        @Schema(
            description = "Timestamp when the bookmark was created",
            type = "string",
            format = "date-time",
            example = "2026-03-03T15:45:00Z"
        )
        Instant createdAt
) {}