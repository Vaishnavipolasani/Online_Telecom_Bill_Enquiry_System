package com.otbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otbs.model.Customer;
 import com.otbs.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public CustomerService( CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	//create customer
	public  Customer applyForConnection(Customer customer) {
		try {
		return customerRepository.save(customer);
		}
		catch (Exception e) {
			throw new RuntimeException("failed to create customer");
 		}
	}
	
	
	//update customer
	public Customer updateCustomer(Customer customer, int customerId) {
		try {
			Customer existingCustomer = customerRepository.getById(customerId);
			
			if(existingCustomer == null) {
				throw new RuntimeException("customer not found for customerId: " +customerId);
			}
		
		existingCustomer.setName(customer.getName());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setPhoneNumber(customer.getPhoneNumber());
		existingCustomer.setAddress(customer.getAddress());
		
		return customerRepository.save(existingCustomer);
		}
		
		catch (Exception e) {
			throw new RuntimeException("failed to update cutomer details");
 		}
	}
	
	  

}
