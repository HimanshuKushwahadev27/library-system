package com.emi.Search_service.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BookSearchRequestDto(

        @Size(min = 1, max = 100)
        @Schema(
                description = "Full text search query (searches in title, author, description)",
                example = "spring boot"
        )
        String query,

        @Size(max = 100)
        @Schema(
                description = "Filter by genre name (exact match)",
                example = "Programming"
        )
        String genreName,

        @Pattern(
                regexp = "DRAFT|ONGOING|COMPLETED",
                message = "lifeCycleStatus must be DRAFT, ONGOING or COMPLETED"
        )
        @Schema(
                description = "Filter by lifecycle status",
                example = "ONGOING",
                allowableValues = {"DRAFT", "ONGOING", "COMPLETED"}
        )
        String lifeCycleStatus,

        @Pattern(
                regexp = "PRIVATE|PUBLIC",
                message = "visibilityStatus must be PRIVATE or PUBLIC"
        )
        @Schema(
                description = "Filter by visibility status",
                example = "PUBLIC",
                allowableValues = {"PRIVATE", "PUBLIC"}
        )
        String visibilityStatus,

        @DecimalMin(value = "0.0", inclusive = true, message = "minPrice must be >= 0")
        @Schema(
                description = "Minimum price filter",
                example = "100"
        )
        Double minPrice,

        @DecimalMin(value = "0.0", inclusive = true, message = "maxPrice must be >= 0")
        @Schema(
                description = "Maximum price filter",
                example = "500"
        )
        Double maxPrice,

        @Size(max = 150)
        @Schema(
                description = "Filter by author name (exact match)",
                example = "Robert Martin"
        )
        String authorNames

) {}