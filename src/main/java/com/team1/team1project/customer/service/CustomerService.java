package com.team1.team1project.customer.service;

import com.team1.team1project.customer.domain.Customer;
import com.team1.team1project.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	// 고객 리스트 조회
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	// 고객 ID로 조회
	public Optional<Customer> getCustomerById(int customerId) {
		return customerRepository.findById(customerId);
	}

	// 고객 추가
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	// 고객 수정
	public Customer updateCustomer(int customerId, Customer customer) {
		if (customerRepository.existsById(customerId)) {
			customer.setCustomerId(customerId);
			return customerRepository.save(customer);
		}
		return null;  // 고객이 존재하지 않으면 null 반환
	}

	// 고객 삭제
	public boolean deleteCustomer(int customerId) {
		if (customerRepository.existsById(customerId)) {
			customerRepository.deleteById(customerId);
			return true;
		}
		return false;
	}
}
