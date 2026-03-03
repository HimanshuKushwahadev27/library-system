package com.emi.payment_service.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emi.payment_service.RequestDto.RequestPaymentDto;
import com.emi.payment_service.ResponseDto.ResponsePaymentDto;
import com.emi.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

	private final PaymentService paymentService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponsePaymentDto> create(
			@RequestBody RequestPaymentDto request,
			@AuthenticationPrincipal Jwt jwt,
			@RequestHeader("Idempotency-Key") String IdempotencyKey
			) {
		return ResponseEntity.ok(
				paymentService
				.create(
						request, UUID.fromString(IdempotencyKey), UUID.fromString(jwt.getSubject()))
				);
	}
	
	@PostMapping("/webhook")
	public ResponseEntity<Void> webhook(
	        @RequestBody String payload,
	        @RequestHeader("Stripe-Signature") String sigHeader) {
		paymentService.webhook(payload, sigHeader);
	    return ResponseEntity.ok().build();
	}
	
	@GetMapping("/get/{paymentId}")
	public ResponseEntity<ResponsePaymentDto> getPayment(@PathVariable UUID paymentId, @RequestHeader("X-User-Id") String keycloakId) {
		return ResponseEntity.ok(paymentService.getPayment(paymentId, UUID.fromString(keycloakId)));
	}
	
	
	
}
