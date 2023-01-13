package com.kunal.springboot.ws.service;

import com.kunal.customers.CreateCustomerRequest;
import com.kunal.springboot.ws.dto.Customer;
import com.kunal.springboot.ws.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author kunal
 * @project SpringBoot-SOAP-WS-main
 */
@Service
public class CustomerDetailsService {

    private static final List<Customer> customers = new ArrayList<>();

    static {
        Customer customer1 = new Customer(1, "Kumar", "test");
        Customer customer2 = new Customer(2, "Kunal", "test2");
        Customer customer3 = new Customer(3, "Kumar Kunal", "test3");
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
    }

    // Create Customer
    public com.kunal.customers.Status createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer();
        if (customer!=null) {
            customer.setId(request.getCustomerDetails().getId());
            customer.setCustomerCR(request.getCustomerDetails().getCustomerCR());
            customer.setPassword(request.getCustomerDetails().getPassword());
            return com.kunal.customers.Status.SUCCESS;
        }
        return com.kunal.customers.Status.FAIL;
    }

    // Get customer by id
    public Customer getCustomerById(int customerId) {
        return customers.stream().filter(c -> c.getId() == customerId).findAny()
                .orElseThrow(() -> new CustomerNotFoundException("Invalid Customer Id Queried {} " + customerId));
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customers;
    }

    // Deleting a specific customer
    public Status deleteCustomerById(int id) {

        Iterator<Customer> customerIterator = customers.iterator();
        while (customerIterator.hasNext()) {
            Customer customer = customerIterator.next();
            if (customer.getId() == id) {
                customerIterator.remove();
                return Status.SUCCESS;
            }
        }
        return Status.FAILS;
    }

    public enum Status {
        SUCCESS, FAILS
    }
}
