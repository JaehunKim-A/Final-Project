package com.team1.team1project.customer.service;

import com.team1.team1project.domain.Customer;
import com.team1.team1project.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Optional<Customer> getCustomerById(int customerId) {
		return customerRepository.findById(customerId);
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(int customerId, Customer customer) {
		if (customerRepository.existsById(customerId)) {
			customer.setCustomerId(customerId);
			return customerRepository.save(customer);
		}
		return null;
	}

	@Override
	public boolean deleteCustomer(int customerId) {
		if (customerRepository.existsById(customerId)) {
			customerRepository.deleteById(customerId);
			return true;
		}
		return false;
	}

	// ✅ 페이지네이션 메서드 구현
	@Override
	public Page<Customer> getCustomerPage(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}
}
