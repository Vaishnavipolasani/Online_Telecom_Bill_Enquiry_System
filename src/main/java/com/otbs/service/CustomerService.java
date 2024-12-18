package com.otbs.service;

import com.otbs.model.Customer;
import com.otbs.repository.CustomerRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) {
        Customer registeredCustomer = customerRepository.save(customer);
        System.out.println("Customer registered and email sent to: " + registeredCustomer.getEmail());
        return registeredCustomer;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public Customer authenticate(String username, String password) {
        Customer customer = customerRepository.findByUsernameAndPassword(username, password);
        if (customer != null) {
            return customer;
        }
        return null;
    }
}

