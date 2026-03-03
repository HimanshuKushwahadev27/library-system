package com.emi.payment_service.kafkaEvent;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.emi.events.payment.PaymentGeneratedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentEventGeneration {

	private final KafkaTemplate<String, PaymentGeneratedEvent> kafkaEventSuccess;
	private final KafkaTemplate<String, PaymentGeneratedEvent> kafkaEventFailed;

	
	public void paymentSuccess(PaymentGeneratedEvent event) {
		kafkaEventSuccess.send("Payment-success-event", event);
	}
	
	public void paymentFailed(PaymentGeneratedEvent event) {
		kafkaEventFailed.send("Payment-failed-event", event);
	}
}
