package com.emi.order_service.kafkaEvent;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.emi.events.catalog.ContentPurchasedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProduceCatalogEvent {

	private final KafkaTemplate<String, ContentPurchasedEvent> kafkaEventSuccess;

	public void orderSuccess(ContentPurchasedEvent event) {
		kafkaEventSuccess.send("Content-Purchased-event", event);
	}
}
