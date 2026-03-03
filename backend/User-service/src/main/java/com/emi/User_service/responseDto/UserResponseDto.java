package com.emi.User_service.responseDto;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User profile response")
public record UserResponseDto(

        @Schema(
                description = "Unique user identifier",
                example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        )
        UUID id,

        @Schema(
                description = "User display name",
                example = "Himanshu Kushwaha"
        )
        String displayName,

        @Schema(
                description = "Profile image URL",
                example = "https://cdn.inkly.com/profile/123.png",
                nullable = true
        )
        String imageurl,

        @Schema(
                description = "Short user biography",
                example = "Backend developer and book lover",
                nullable = true
        )
        String bio,

        @Schema(
                description = "Account creation timestamp",
                example = "2024-03-10T12:30:00Z",
                type = "string",
                format = "date-time"
        )
        Instant createdAt

) {}