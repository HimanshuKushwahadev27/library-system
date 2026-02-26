package com.emi.order_service.exceptions;

public class OrderExistsException extends RuntimeException {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	public OrderExistsException(String msg) {
		super(msg);
	}

}
