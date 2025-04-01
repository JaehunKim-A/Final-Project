package com.team1.team1project.customer.service;

import com.team1.team1project.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
	List<CustomerDTO> getAllCustomers();
	Optional<CustomerDTO> getCustomerById(int customerId);
	CustomerDTO createCustomer(CustomerDTO customerDTO);
	CustomerDTO updateCustomer(int customerId, CustomerDTO customerDTO);
	boolean deleteCustomer(int customerId);
}
