package com.emi.order_service.kafkaEvent;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.emi.events.payment.PaymentGeneratedEvent;
import com.emi.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumePaymentEvent {

	private final OrderService orderService;
	
	@KafkaListener(topics ="Payment-success-event" ,groupId="orderService")
	public void markOrderSuccess(PaymentGeneratedEvent event) {
		orderService.markPaid(UUID.fromString((String)event.getOrderId()), UUID.fromString((String)event.getPaymentId()));
	}
	
	@KafkaListener(topics ="Payment-failed-event" ,groupId="orderService")
	public void markOrderFailed(PaymentGeneratedEvent event) {
		orderService.markFailed(UUID.fromString((String)event.getOrderId()), UUID.fromString((String)event.getPaymentId()));
	}
}
