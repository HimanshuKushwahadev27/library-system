package com.emi.Authoring_service.exceptions;

public class DraftNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public DraftNotFoundException(String msg) {
		super(msg);
	}
}
