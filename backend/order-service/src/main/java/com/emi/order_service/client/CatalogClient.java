package com.emi.order_service.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.emi.order_service.ResponseDto.CatalogPriceResponse;



@FeignClient(value = "catalog-service", url = "http://catalog-service:8080")
public interface CatalogClient {

	
	@GetMapping(value="/api/book/internal/{bookId}")
	public CatalogPriceResponse getBookInternal(
			@PathVariable UUID bookId);

	@GetMapping(value="/api/book/contents/internal/{bookId}/{contentId}")
	public CatalogPriceResponse getBookContentsByContentIdsInternal(@PathVariable UUID bookId, @PathVariable UUID contentId);
}
