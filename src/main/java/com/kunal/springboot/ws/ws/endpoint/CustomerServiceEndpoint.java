package com.kunal.springboot.ws.ws.endpoint;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.kunal.springboot.ws.exception.ServiceFaultException;
import com.kunal.springboot.ws.generated.CreateCustomerRequest;
import com.kunal.springboot.ws.generated.CreateCustomerResponse;
import com.kunal.springboot.ws.generated.ServiceFault;
import com.kunal.springboot.ws.ws.config.WebserviceConfig;

/**
 * Kumar.Kunal
 */

@Endpoint
public class CustomerServiceEndpoint {
	
	private static final Logger logger = LogManager.getLogger(CustomerServiceEndpoint.class);

	@ResponsePayload
	@PayloadRoot(localPart = "createCustomerRequest", namespace = WebserviceConfig.NAMESPACE_URL)
	public CreateCustomerResponse createCustomer(@RequestPayload CreateCustomerRequest createCustomerRequest) {
		
		logger.info("Preparing Customer Create Request under CustomerServiceEndpoint class....  ");

		if (createCustomerRequest != null && !createCustomerRequest.getCustomerCR().isEmpty()
				&& !createCustomerRequest.getCustomerPassword().isEmpty()) {
			
			CreateCustomerResponse createCustomerResponse = new CreateCustomerResponse();
			createCustomerResponse.setCustomerID(BigInteger.ONE);
			createCustomerResponse
					.setDetails(createCustomerRequest.getCustomerName() + "\n" + createCustomerRequest.getCustomerCR());
			createCustomerResponse.setStatus("SUCCESS");
			logger.debug("createCustomerResponse {} ",  createCustomerResponse);
			return createCustomerResponse;

		} else {
			throw new ServiceFaultException("ERROR", new ServiceFault( "BAD_REQUEST ", " Create Customer Request with CR not created :  " + createCustomerRequest.getCustomerCR() ));
			
		}

	}

}
