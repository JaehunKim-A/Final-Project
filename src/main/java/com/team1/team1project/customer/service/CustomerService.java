package com.team1.team1project.customer.service;

import com.team1.team1project.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
	List<Customer> getAllCustomers();
	Optional<Customer> getCustomerById(int customerId);
	Customer createCustomer(Customer customer);
	Customer updateCustomer(int customerId, Customer customer);
	boolean deleteCustomer(int customerId);


}
