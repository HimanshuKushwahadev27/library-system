package com.emi.order_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.order_service.entity.IdempotencyRecord;

public interface IdempotencyRepo extends JpaRepository<IdempotencyRecord, UUID> {


	 Optional<IdempotencyRecord> findByKeycloakIdAndIdempotencyKey(UUID keycloakId, UUID idempotencyId);

}
