package com.emi.Authoring_service.exceptions;

public class ChapterDraftExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChapterDraftExistsException(String msg){
		super(msg);
	}
}
