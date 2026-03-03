package com.emi.order_service.mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.emi.events.catalog.OrderType;
import com.emi.order_service.RequestDto.OrderItemRequestDto;
import com.emi.order_service.RequestDto.RequestOrderDto;
import com.emi.order_service.entity.OrderItem;
import com.emi.order_service.serviceImpl.CatalogService;

@Component
public class OrderItemMapper {

    public List<OrderItem> getEntities(RequestOrderDto request,
                                       CatalogService catalogService,
                                       UUID orderId) {

        return request.items()
                .stream()
                .map(item -> {

                    BigDecimal price = resolvePrice(item, catalogService);

                    return new OrderItem(
                            orderId,
                            price,
                            item.type(),
                            item.bookId(),
                            item.chapterId(),
                            Instant.now(),
                            Instant.now()
                    );
                })
                .toList();
    }

    private BigDecimal resolvePrice(OrderItemRequestDto item,
                                    CatalogService catalogService) {

        if (item.type() == OrderType.BOOK) {
            return catalogService.catalogValidationForBook(item.bookId());
        }

        if (item.type() == OrderType.CONTENT) {
            return catalogService.catalogValidationForContent(
                    item.bookId(),
                    item.chapterId()
            );
        }

        throw new IllegalStateException("Invalid order type");
    }

    public BigDecimal getTotalPrice(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}