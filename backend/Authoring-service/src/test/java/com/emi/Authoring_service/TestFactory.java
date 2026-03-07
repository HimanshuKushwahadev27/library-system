package com.emi.Authoring_service;

import java.math.BigDecimal;
import java.util.UUID;

import com.emi.Authoring_service.RequestDtos.RequestBookCreateDto;
import com.emi.Authoring_service.enums.BookLifeCycleStatus;
import com.emi.Authoring_service.enums.BookVisibilityStatus;

public class TestFactory {
	public static RequestBookCreateDto  validrequest(UUID authorId) {
		return new RequestBookCreateDto (
			       authorId,
		            "Spring Boot Mastery",
		            "This is a complete guide to Spring Boot development.",
		            "9780134350884",
		            new BigDecimal("499.99"),
		            BookLifeCycleStatus.DRAFT,
		            BookVisibilityStatus.PRIVATE,
		            true
				);
	}
}
