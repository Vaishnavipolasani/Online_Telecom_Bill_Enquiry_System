package com.otbs.controller;

import com.otbs.model.Customer;
import com.otbs.service.CustomerService;
import com.otbs.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmailService emailService; // Add EmailService

    @PostMapping("/register")
    public Customer registerCustomer(@RequestBody Customer customer) {
        // Register the customer
        Customer registeredCustomer = customerService.registerCustomer(customer);

        // Send a confirmation email
        emailService.sendThankYouEmail(registeredCustomer.getEmail(), registeredCustomer.getName());

        return registeredCustomer;
    }

    @GetMapping("/viewallcustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer customer) {
        String username = customer.getUsername();
        String password = customer.getPassword();

        Customer authenticatedCustomer = customerService.authenticate(username, password);
        if (authenticatedCustomer != null) {
            return ResponseEntity.ok(authenticatedCustomer);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
