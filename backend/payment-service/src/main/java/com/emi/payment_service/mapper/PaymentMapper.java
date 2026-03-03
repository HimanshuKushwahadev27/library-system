package com.emi.payment_service.mapper;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.emi.events.payment.PaymentStatus;
import com.emi.payment_service.ResponseDto.ResponseOrderDto;
import com.emi.payment_service.ResponseDto.ResponsePaymentDto;
import com.emi.payment_service.entity.Payments;

@Component
public class PaymentMapper {

	public Payments toEntity(ResponseOrderDto request, UUID keycloakId) {
		Payments payment = new Payments();
		
		payment.setAmount(request.totalAmount());
		payment.setCreatedAt(Instant.now());
		payment.setOrderId(request.orderId());
		payment.setStatus(PaymentStatus.INITIATED);
		payment.setUserKeycloakId(keycloakId);
		payment.setUpdatedAt(Instant.now());
		return payment;
	}

	public ResponsePaymentDto toDto(Payments payment) {
		return new ResponsePaymentDto(
				payment.getId(),
				payment.getOrderId(),
				payment.getAmount(),
				payment.getStatus(),
				payment.getGatewayTransactionId().toString(),
				payment.getCreatedAt())
				;
	}

}
