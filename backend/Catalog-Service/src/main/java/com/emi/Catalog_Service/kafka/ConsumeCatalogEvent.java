package com.emi.Catalog_Service.kafka;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.emi.Catalog_Service.Entity.CatalogOwnership;
import com.emi.Catalog_Service.Repository.CatalogOwnershipRepo;
import com.emi.events.catalog.ContentPurchasedEvent;
import com.emi.events.catalog.OrderType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumeCatalogEvent {

	private final CatalogOwnershipRepo catalogOwnerRepo;
	
	@KafkaListener(topics ="Content-Purchased-event" ,groupId="orderService")
	public void markOrderSuccess(ContentPurchasedEvent event) {
		
		
		if(event.getType().equals(OrderType.BOOK)) {
			CatalogOwnership resource =  new CatalogOwnership();
			resource.setBookId(UUID.fromString((String)event.getBookId()));
			resource.setType(event.getType());
			resource.setUserKeycloakId(UUID.fromString((String)event.getUserId()));
			resource.setContentId(null);
			catalogOwnerRepo.save(resource);
		}else {
			
			event.getChapterIds().stream().forEach(t -> {
				CatalogOwnership resource =  new CatalogOwnership();
				resource.setBookId(UUID.fromString((String)event.getBookId()));
				resource.setType(event.getType());
				resource.setUserKeycloakId(UUID.fromString((String)event.getUserId()));
				resource.setContentId(UUID.fromString((String)t));
				catalogOwnerRepo.save(resource);	
			});
		}
		
	}
	
}
