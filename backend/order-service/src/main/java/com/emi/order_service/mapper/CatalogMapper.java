package com.emi.order_service.mapper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.emi.events.catalog.ContentPurchasedEvent;
import com.emi.order_service.entity.Order;
import com.emi.order_service.entity.OrderItem;

@Component
public class CatalogMapper {

	public ContentPurchasedEvent getEvent(Order order, List<OrderItem> items) {
		
		List<CharSequence> chapterIds = items.stream()
		        .map(OrderItem::getChapterId)
		        .filter(Objects::nonNull)
		        .map(id -> (CharSequence) id.toString())
		        .toList();
		
		return new ContentPurchasedEvent(
				items.getFirst().getBookId().toString(),
				order.getUserKeycloakId().toString(),
				chapterIds,
				items.getFirst().getItemType()
				);
	}

}
