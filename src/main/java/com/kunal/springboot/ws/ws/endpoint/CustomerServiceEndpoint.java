package com.kunal.springboot.ws.ws.endpoint;

import java.math.BigInteger;

import com.kunal.springboot.ws.generated.CreateCustomerRequest;
import com.kunal.springboot.ws.generated.CreateCustomerResponse;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Kumar.Kunal
 */

@Endpoint
public class CustomerServiceEndpoint {
	
	@ResponsePayload
	@PayloadRoot(localPart="createCustomerRequest", namespace="http://kunal.soapws.com/types/v1")
	public CreateCustomerResponse createCustomer( @RequestPayload CreateCustomerRequest request) {
		
		CreateCustomerResponse response = new CreateCustomerResponse();
		response.setCustomerID(BigInteger.ONE);
		response.setDetails(request.getCustomerName() + " " + request.getCustomerCR());
		response.setStatus("SUCCESS");
		// Add the logic of CreateCustomerFault for catching dynamically the response status codes.
		return response;
	}

}
