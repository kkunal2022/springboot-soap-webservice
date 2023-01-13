package com.kunal.springboot.ws.endpoint;

import com.kunal.customers.CreateCustomerRequest;
import com.kunal.customers.CreateCustomerResponse;
import com.kunal.customers.CustomerDetails;
import com.kunal.customers.DeleteCustomerDetailsRequest;
import com.kunal.customers.DeleteCustomerDetailsResponse;
import com.kunal.customers.GetAllCustomerDetailsRequest;
import com.kunal.customers.GetAllCustomerDetailsResponse;
import com.kunal.customers.GetCustomerDetailsRequest;
import com.kunal.customers.GetCustomerDetailsResponse;
import com.kunal.customers.Status;
import com.kunal.springboot.ws.dto.Customer;
import com.kunal.springboot.ws.service.CustomerDetailsService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * @author kunal
 * @project SpringBoot-SOAP-WS-main
 */
@Endpoint
public class CustomerDetailsEndpoint {

    private CustomerDetailsService customerDetailsService;

    public CustomerDetailsEndpoint(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    @PayloadRoot(namespace = "https://www.kunal.soapws.com/customers", localPart = "CreateCustomerRequest")
    @ResponsePayload
    public CreateCustomerResponse createCustomer(@RequestPayload CreateCustomerRequest createCustomerRequest) {
        CreateCustomerResponse createCustomerResponse = new CreateCustomerResponse();
        createCustomerResponse.setStatus(customerDetailsService.createCustomer(createCustomerRequest));
        return createCustomerResponse;
    }

    @PayloadRoot(namespace = "https://www.kunal.soapws.com/customers", localPart = "GetCustomerDetailsRequest")
    @ResponsePayload
    public GetCustomerDetailsResponse getCustomerDetailsResponse(@RequestPayload GetCustomerDetailsRequest getCustomerDetailsRequest) {
        return customerResponseMapper(customerDetailsService.getCustomerById(getCustomerDetailsRequest.getId()));
    }

    @PayloadRoot(namespace = "https://www.kunal.soapws.com/customers", localPart = "GetAllCustomerDetailsRequest")
    @ResponsePayload
    public GetAllCustomerDetailsResponse processGetAllCustomerDetailsRequest(@RequestPayload GetAllCustomerDetailsRequest getAllCourseDetailsRequest) {
        List<Customer> customers = customerDetailsService.getAllCustomers();
        return allCustomerResponseMapper(customers);
    }

    @PayloadRoot(namespace = "https://www.kunal.soapws.com/customers", localPart = "DeleteCustomerDetailsRequest")
    @ResponsePayload
    public DeleteCustomerDetailsResponse deleteCustomerDetailsRequest(@RequestPayload DeleteCustomerDetailsRequest deleteCustomerDetailsRequest) {

        DeleteCustomerDetailsResponse deleteCustomerDetailsResponse = new DeleteCustomerDetailsResponse();
        deleteCustomerDetailsResponse.setStatus(mapCustomerStatus(customerDetailsService.deleteCustomerById(deleteCustomerDetailsRequest.getId())));
        return deleteCustomerDetailsResponse;
    }

    private Status mapCustomerStatus(CustomerDetailsService.Status status) {
        /*
        if (status == CustomerDetailsService.Status.SUCCESS) {
            return Status.SUCCESS;
        }
        return Status.FAIL;
        */

        return status == CustomerDetailsService.Status.SUCCESS ? Status.SUCCESS : Status.FAIL;
    }

    private CustomerDetails customerMapper(Customer customer) {
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setId(customer.getId());
        customerDetails.setCustomerCR(customer.getCustomerCR());
        customerDetails.setPassword(customer.getPassword());
        return customerDetails;
    }

    private GetCustomerDetailsResponse customerResponseMapper(Customer customer) {
        GetCustomerDetailsResponse getCustomerDetailsResponse = new GetCustomerDetailsResponse();
        getCustomerDetailsResponse.setCustomerDetails(customerMapper(customer));
        return getCustomerDetailsResponse;
    }

    private GetAllCustomerDetailsResponse allCustomerResponseMapper(List<Customer> customers) {
        GetAllCustomerDetailsResponse getAllCustomerDetailsResponse = new GetAllCustomerDetailsResponse();
        customers.stream().map(this::customerMapper).forEachOrdered(customerDetails -> getAllCustomerDetailsResponse.getCustomerDetails().add(customerDetails));
        return getAllCustomerDetailsResponse;
    }
}
