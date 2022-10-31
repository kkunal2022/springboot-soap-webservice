package com.kunal.springboot.ws.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

import com.kunal.springboot.ws.generated.CreateCustomerFault;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

/**
 * Kumar.Kunal
 */

@SoapFault(faultCode = FaultCode.SERVER)
public class InvalidInputException extends Exception {
	

	private static final long serialVersionUID = -6981932893412446560L;
	
	private CreateCustomerFault createCustomerFault;

    public InvalidInputException(String message, CreateCustomerFault createCustomerFault) {
        super(message);
        this.createCustomerFault = createCustomerFault;
    }

    public InvalidInputException(String message, Throwable cause, CreateCustomerFault createCustomerFault) {
        super(message, cause);
        this.createCustomerFault = createCustomerFault;
    }

    public CreateCustomerFault getcreateCustomerFault() {
        return createCustomerFault;
    }

    public void setcreateCustomerFault(CreateCustomerFault createCustomerFault) {
        this.createCustomerFault = createCustomerFault;
    }
}
