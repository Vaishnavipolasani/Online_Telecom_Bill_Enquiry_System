package com.otbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otbs.model.Customer;
import com.otbs.service.CustomerService;

@RestController
@RequestMapping("api/customers/")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService=customerService;
	}
	
	@PostMapping("/register")
    public Customer registerCustomer(@RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
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

	
	@PostMapping("apply")
	public  Customer applyForConnection(@RequestBody Customer customer) {
		return customerService.applyForConnection(customer);
	}
	
	@PutMapping("update/{customerId}")
	public Customer updateCustomer(@RequestBody Customer customer,@PathVariable int customerId) {
		return customerService.updateCustomer(customer, customerId);
	}



}
