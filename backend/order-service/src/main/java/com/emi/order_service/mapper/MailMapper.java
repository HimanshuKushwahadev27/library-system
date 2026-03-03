package com.emi.order_service.mapper;

import org.springframework.stereotype.Component;

import com.emi.events.mail.MailEvent;
import com.emi.order_service.entity.Order;
import com.emi.order_service.entity.OrderItem;


@Component
public class MailMapper {

	
	public MailEvent getEvent(Order order, OrderItem orderItem) {
		MailEvent event = new MailEvent();
		
		event.setFirstName(order.getFirstName());
		event.setLastName(order.getFirstName());
		event.setEmail(order.getUserEmail());
		event.setOrderType(orderItem.getItemType().toString());
		event.setPrice(order.getAmount().doubleValue());
		return event;
	}

}
