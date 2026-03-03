package com.emi.User_service.responseDto;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO representing a book review submitted by a user")
public record ResponseReviewDto(

        @Schema(
            description = "Unique identifier of the review",
            example = "1fa85f64-5717-4562-b3fc-2c963f66afa1"
        )
        UUID id,

        @Schema(
            description = "Unique identifier of the reviewed book",
            example = "2fa85f64-5717-4562-b3fc-2c963f66afa2"
        )
        UUID bookId,

        @Schema(
            description = "Unique identifier of the user who submitted the review",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa3"
        )
        UUID userId,

        @Schema(
            description = "Rating given to the book (1 to 5)",
            example = "5",
            minimum = "1",
            maximum = "5"
        )
        Integer rating,

        @Schema(
            description = "User's review comment about the book",
            example = "Excellent book with deep insights and great storytelling."
        )
        String comment,

        @Schema(
            description = "Timestamp when the review was created",
            type = "string",
            format = "date-time",
            example = "2026-03-03T14:30:00Z"
        )
        Instant createdAt
) {}