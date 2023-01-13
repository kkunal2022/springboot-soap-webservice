package com.kunal.springboot.ws.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
/**
 * @author kunal
 * @project SpringBoot-SOAP-WS-main
 */
@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{https://www.kunal.soapws.com/customers}0_CUSTOMER_NOT_FOUND")
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
