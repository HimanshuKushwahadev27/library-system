package com.emi.Catalog_Service.Entity;


import java.util.UUID;

import com.emi.events.catalog.OrderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="catalog_ownership")
public class CatalogOwnership {

	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(name="book_id", nullable=false)
	private UUID bookId;
	
	@Column(name="user_keycloak_id", nullable=false)
	private UUID userKeycloakId;
	
	@Column(name="content_id", nullable=false)
	private UUID contentId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type", nullable=false)
	private OrderType type;
	
}
