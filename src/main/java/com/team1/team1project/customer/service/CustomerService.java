package com.team1.team1project.customer.service;

import com.team1.team1project.customer.domain.Customer;
import com.team1.team1project.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
}
