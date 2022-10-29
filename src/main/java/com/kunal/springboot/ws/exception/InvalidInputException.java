package com.kunal.springboot.ws.exception;

/**
 * Kumar.Kunal
 */

public class InvalidInputException extends Exception {
	

	private static final long serialVersionUID = -6981932893412446560L;
	
	private final String errorDetails;
	
	public InvalidInputException(String reason , String errorDetails) {
		super(reason);
		this.errorDetails = errorDetails;
	}
	
	public String getFaultInfo() {
		return errorDetails;
	}

}
