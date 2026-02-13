package com.emi.Authoring_service.exceptions;

public class BookAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookAlreadyExistsException(String msg) {
		super(msg);
	}
	
}
