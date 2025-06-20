package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.Customer;
import com.lorachemicals.Backend.model.SalesRep;
import com.lorachemicals.Backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Save or update a customer
    public void saveCustomer(Customer newCustomer) {
        customerRepository.save(newCustomer);
    }

    // Fetch a customer by ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    // Delete customer by ID
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // Fetch all customers (for list display)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomersBySalesRep(SalesRep salesRep) {
        return customerRepository.findBySalesRep(salesRep);
    }

}
