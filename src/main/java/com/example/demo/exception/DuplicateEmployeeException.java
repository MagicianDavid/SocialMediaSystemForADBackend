package com.example.demo.exception;

public class DuplicateEmployeeException extends DuplicateTypeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3326186321856567306L;

	public DuplicateEmployeeException(String message) {
		super(message);
	}

}
