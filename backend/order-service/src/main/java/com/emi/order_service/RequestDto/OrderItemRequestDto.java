package com.emi.order_service.RequestDto;

import java.util.UUID;

import com.emi.events.catalog.OrderType;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDto(

        @NotNull(message = "Book ID is required")
        UUID bookId,

        UUID chapterId,

        @NotNull(message = "Order type is required")
        OrderType type

) {

    @AssertTrue(message = "Invalid combination of bookId/chapterId and type")
    public boolean isValidCombination() {

        if (type == OrderType.BOOK) {
            return chapterId == null;
        }

        if (type == OrderType.CONTENT) {
            return chapterId != null;
        }

        return false;
    }
}