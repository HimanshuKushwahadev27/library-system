package com.emi.order_service.exceptions;

public class UnauthorizedAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedAccessException(String msg){
		super(msg);
	}

}
