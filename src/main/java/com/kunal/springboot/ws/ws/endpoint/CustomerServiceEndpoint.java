package com.kunal.springboot.ws.ws.endpoint;

import java.math.BigInteger;
import java.util.Optional;

import com.kunal.springboot.ws.exception.InvalidInputException;
import com.kunal.springboot.ws.exception.ServiceFaultException;
import com.kunal.springboot.ws.generated.CreateCustomerFault;
import com.kunal.springboot.ws.generated.CreateCustomerRequest;
import com.kunal.springboot.ws.generated.CreateCustomerResponse;
import com.kunal.springboot.ws.generated.ServiceFault;

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
	@PayloadRoot(localPart = "createCustomerRequest", namespace = "http://kunal.soapws.com/types/v1")
	public CreateCustomerResponse createCustomer(@RequestPayload CreateCustomerRequest createCustomerRequest)
			throws InvalidInputException {

		CreateCustomerFault createCustomerFault = new CreateCustomerFault();

		if (createCustomerRequest != null && !createCustomerRequest.getCustomerCR().isEmpty()
				&& !createCustomerRequest.getCustomerPassword().isEmpty()) {
			
			CreateCustomerResponse createCustomerResponse = new CreateCustomerResponse();
			createCustomerResponse.setCustomerID(BigInteger.ONE);
			createCustomerResponse
					.setDetails(createCustomerRequest.getCustomerName() + "\n" + createCustomerRequest.getCustomerCR());
			createCustomerResponse.setStatus("SUCCESS");
			//createCustomerResponse.setStatus(Integer.toString(createCustomerFault.getErrorCode()) + "\n - " + createCustomerFault.getErrorMessage());
			return createCustomerResponse;

		} else {
			String errorMessage = createCustomerFault.setErrorMessage("FAILED");
			throw new InvalidInputException("ERROR In getting the user details " , errorMessage);
			
		}

	}

}
