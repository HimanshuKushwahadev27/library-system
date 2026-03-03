package com.emi.order_service.repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.emi.order_service.entity.IdempotencyRecord;

public interface IdempotencyRepo extends JpaRepository<IdempotencyRecord, UUID> {


	 Optional<IdempotencyRecord> findByKeycloakIdAndIdempotencyKey(UUID keycloakId, UUID idempotencyId);

	 @Modifying
	 @Query("DELETE FROM IdempotencyRecord i WHERE i.expiresAt < :now")
	 void deleteExpired(Instant now);
	 
	 

}
