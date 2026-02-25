package com.emi.Search_service.exceptions;

public class DocumentNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DocumentNotFoundException(String msg){
		super(msg);
	}

}
