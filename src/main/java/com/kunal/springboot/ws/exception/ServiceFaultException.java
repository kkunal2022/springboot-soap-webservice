package com.kunal.springboot.ws.exception;

import com.kunal.springboot.ws.generated.ServiceFault;

/**
 * Kumar.Kunal
 */

public class ServiceFaultException extends RuntimeException {

    private static final long serialVersionUID = -915192026117183870L;
    
	private ServiceFault serviceFault;

    public ServiceFaultException(String message, ServiceFault serviceFault) {
        super(message);
        this.serviceFault = serviceFault;
    }

    public ServiceFaultException(String message, Throwable e, ServiceFault serviceFault) {
        super(message, e);
        this.serviceFault = serviceFault;
    }

    public ServiceFault getServiceFault() {
        return serviceFault;
    }

    public void setServiceFault(ServiceFault serviceFault) {
        this.serviceFault = serviceFault;
    }
}