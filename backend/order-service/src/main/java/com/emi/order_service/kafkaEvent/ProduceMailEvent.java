package com.emi.order_service.kafkaEvent;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.emi.events.mail.MailEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProduceMailEvent {

	private final KafkaTemplate<String, MailEvent> kafkaMailSender;

	
	public void sendEventMail(MailEvent event) {
		kafkaMailSender.send("Mail-event", event);
	}
}
