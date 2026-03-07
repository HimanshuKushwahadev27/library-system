package com.emi.order_service.serviceImpl;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.emi.order_service.ResponseDto.CatalogPriceResponse;
import com.emi.order_service.client.CatalogClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CatalogService {

	private final CatalogClient catalogClient;
	
	
	public BigDecimal catalogValidationForBook(UUID bookId) {
		
		CatalogPriceResponse catalogResponse = catalogClient.getBookInternal(bookId);
		
		return catalogResponse.price();
	}
	
	public BigDecimal catalogValidationForContent(UUID bookId, UUID chapterId) {
		
		CatalogPriceResponse catalogResponse = catalogClient
				.getBookContentsByContentIdsInternal(bookId, chapterId);
		
		return catalogResponse.price();
	}
}
