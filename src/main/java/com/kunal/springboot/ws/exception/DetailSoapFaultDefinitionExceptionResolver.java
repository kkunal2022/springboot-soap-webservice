package com.kunal.springboot.ws.exception;

import javax.xml.namespace.QName;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import com.kunal.springboot.ws.generated.ServiceFault;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Kumar.Kunal
 */

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {
	
	private static final Logger logger = LogManager.getLogger(DetailSoapFaultDefinitionExceptionResolver.class);

	private static final QName CODE = new QName("code");
	private static final QName DESCRIPTION = new QName("description");

	@Override
	protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
		logger.warn("Exception processed under DetailSoapFaultDefinitionExceptionResolver class ", ex);
		if (ex instanceof ServiceFaultException) {
			ServiceFault serviceFault = ((ServiceFaultException) ex).getServiceFault();
			SoapFaultDetail detail = fault.addFaultDetail();
			detail.addFaultDetailElement(CODE).addText(serviceFault.getCode());
			detail.addFaultDetailElement(DESCRIPTION).addText(serviceFault.getDescription());
		}
	}
}
