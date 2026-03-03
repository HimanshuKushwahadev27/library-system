package com.emi.payment_service.serviceImpl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.emi.events.payment.PaymentStatus;
import com.emi.payment_service.RequestDto.GatewayPaymentRequest;
import com.emi.payment_service.RequestDto.RequestPaymentDto;
import com.emi.payment_service.ResponseDto.GatewayResponse;
import com.emi.payment_service.ResponseDto.ResponseOrderDto;
import com.emi.payment_service.ResponseDto.ResponsePaymentDto;
import com.emi.payment_service.client.OrderClient;
import com.emi.payment_service.entity.IdempotencyRecord;
import com.emi.payment_service.entity.Payments;
import com.emi.payment_service.enums.IdempotencyStatus;
import com.emi.payment_service.exceptions.PaymentNotFoundException;
import com.emi.payment_service.exceptions.UnauthorizedException;
import com.emi.payment_service.gateway.PaymentGateway;
import com.emi.payment_service.kafkaEvent.PaymentEventGeneration;
import com.emi.payment_service.mapper.EventMapper;
import com.emi.payment_service.mapper.GatewayMapper;
import com.emi.payment_service.mapper.IdempotencyMapper;
import com.emi.payment_service.mapper.PaymentMapper;
import com.emi.payment_service.repository.IdempotencyRepo;
import com.emi.payment_service.repository.PaymentRepo;
import com.emi.payment_service.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	@Value("${webhook.secret-Key}")
	private String webhookSecret;
	private final EventMapper eventMapper;
	private final PaymentEventGeneration eventGeneration;
	private final PaymentGateway paymentGateway;
	private final ObjectMapper objectMapper;
	private final IdempotencyRepo idempRepo;
	private final PaymentRepo paymentRepo;
	private final IdempotencyMapper idempMapper;
	private final PaymentMapper paymentMapper;
	private final OrderClient orderClient;
	private final GatewayMapper gatewayMapper;

	@Override
	public ResponsePaymentDto create(RequestPaymentDto request, UUID idempotencyId, UUID keycloakId) {
		IdempotencyRecord idempotency = idempMapper.getEntity(request, idempotencyId, keycloakId);

		try {
			idempRepo.save(idempotency);
		} catch (DataIntegrityViolationException ex) {
			IdempotencyRecord existing = idempRepo.findByKeycloakIdAndIdempotencyKey(keycloakId, idempotencyId)
					.orElseThrow();

			if (existing.getStatus() == IdempotencyStatus.COMPLETED) {
				try {
					return objectMapper.readValue(existing.getResponseBody(), ResponsePaymentDto.class);
				} catch (JsonProcessingException e) {
					throw new RuntimeException("Failed to deserialize JSON", e);
				}
			}
			throw new IllegalStateException("Request already in progress");
		}

		ResponseOrderDto order = orderClient.orderVaidation(request.orderId());
		if (!order.userId().equals(keycloakId)) {
			throw new UnauthorizedException("No authorized");
		}
		Payments payment = paymentMapper.toEntity(order, keycloakId);
		paymentRepo.save(payment);
		
		GatewayPaymentRequest gatewayRequest = gatewayMapper.getRequest(payment, idempotencyId);
		GatewayResponse gatewayResponse = paymentGateway.charge(gatewayRequest);
		
		payment.setGatewayTransactionId(gatewayResponse.transactionId());
		ResponsePaymentDto response = paymentMapper.toDto(payment);
		
		idempMapper.updateIdemp(idempotency, response);
		idempRepo.save(idempotency);

		return response;
	}

	@Override
	public ResponsePaymentDto getPayment(UUID paymentId, UUID keycloakId) {
		Payments payment = paymentRepo.findById(paymentId)
				.orElseThrow(() -> new PaymentNotFoundException("no data for the id " + paymentId));

		if (!payment.getUserKeycloakId().equals(keycloakId)) {
			throw new UnauthorizedException("U r not authorized");
		}

		return paymentMapper.toDto(payment);
	}

	@Override
	public void webhook(String payload, String sigHeader) {
		Event event;

		try {
			event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
		} catch (SignatureVerificationException ex) {
			throw new RuntimeException("Invalid Stripe Signature");
		}

		handleEvent(event);
	}

	private void handleEvent(Event event) {

		switch (event.getType()) {
		case "payment_intent.succeeded" -> handleSuccess(event);

		case "payment_intent.payment_failed" -> handleFailure(event);

		}
	}

	private void handleFailure(Event event) {
		
		PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElseThrow();

		String transactionId = intent.getId();

		Payments payment = paymentRepo.findByGatewayTransactionId(transactionId);

		if (payment.getStatus() == PaymentStatus.SUCCESS) {
			return;
		}
		
		payment.setStatus(PaymentStatus.FAILED);
		payment.setUpdatedAt(Instant.now());
		paymentRepo.save(payment);
		
		eventGeneration.paymentFailed(eventMapper.getSuccess(payment));
	}

	private void handleSuccess(Event event) {
		PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElseThrow();

		String transactionId = intent.getId();

		Payments payment = paymentRepo.findByGatewayTransactionId(transactionId);

		if (payment.getStatus() == PaymentStatus.SUCCESS) {
			return;
		}

		payment.setStatus(PaymentStatus.SUCCESS);
		payment.setUpdatedAt(Instant.now());
		paymentRepo.save(payment);

		eventGeneration.paymentFailed(eventMapper.getFailure(payment));

	}

}
