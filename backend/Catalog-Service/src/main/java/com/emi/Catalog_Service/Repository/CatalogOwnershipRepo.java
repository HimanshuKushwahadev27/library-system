package com.emi.Catalog_Service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.Catalog_Service.Entity.CatalogOwnership;

public interface CatalogOwnershipRepo extends JpaRepository<CatalogOwnership, UUID> {

	boolean existsByUserKeycloakIdAndContentId(UUID keycloakId, UUID contentId);

	boolean existsByUserKeycloakIdAndBookId(UUID keycloakId, UUID bookId);

}
