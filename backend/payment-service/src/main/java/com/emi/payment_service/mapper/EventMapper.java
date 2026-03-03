package com.emi.payment_service.mapper;

import org.springframework.stereotype.Component;

import com.emi.events.payment.PaymentGeneratedEvent;
import com.emi.payment_service.entity.Payments;

@Component
public class EventMapper {

	public PaymentGeneratedEvent getSuccess(Payments payment) {
		return new PaymentGeneratedEvent(
				payment.getOrderId().toString(),
				payment.getId().toString(),
				payment.getStatus()
				);
	}

	public PaymentGeneratedEvent getFailure(Payments payment) {
		return new PaymentGeneratedEvent(
				payment.getOrderId().toString(),
				payment.getId().toString(),
				payment.getStatus()
				);
	}

}
