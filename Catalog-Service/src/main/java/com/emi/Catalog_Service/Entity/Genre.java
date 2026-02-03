package com.emi.Catalog_Service.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="genres")
public class Genre {

	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	
	@Column(name="description", nullable=false)
	private String description;
	
}
