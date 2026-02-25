package com.emi.Catalog_Service.ServiceImpl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.emi.events.BookDeletedEvent;
import com.emi.events.BookPublishedEvent;
import com.emi.events.BookUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogProducerService {

	private final KafkaTemplate<String, BookPublishedEvent> kafkaEvent;
	private final KafkaTemplate<String, BookUpdatedEvent> kafkaUpdateEvent;
	private final KafkaTemplate<String, BookDeletedEvent> kafkaDeletedEvent;
	
	public void sendBookCreatedEvent(BookPublishedEvent event) {
		kafkaEvent.send("Book-create-event", event);
	}
	
	public void sendBookUpdatedEvent(BookUpdatedEvent event) {
		kafkaUpdateEvent.send("Book-update-event", event);
	}
	
	public void sendBookDeletedEvent(BookDeletedEvent event) {
		kafkaDeletedEvent.send("Book-delete-event", event);
	}
	
}
