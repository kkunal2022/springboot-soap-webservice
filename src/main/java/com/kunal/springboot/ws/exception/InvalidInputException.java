package com.kunal.springboot.ws.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

/**
 * Kumar.Kunal
 */

@SoapFault(faultCode = FaultCode.SERVER)
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
