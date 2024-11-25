package com.otbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otbs.model.Customer;
import com.otbs.service.CustomerService;

@RestController
@RequestMapping("/customer/")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService=customerService;
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
