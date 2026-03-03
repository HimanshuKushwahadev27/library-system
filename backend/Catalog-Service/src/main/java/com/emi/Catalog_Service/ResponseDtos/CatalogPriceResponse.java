package com.emi.Catalog_Service.ResponseDtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;



public record CatalogPriceResponse(
        UUID bookId,
        List<UUID> chapterId,
        BigDecimal price
		) {

}
