package com.team1.team1project.service.customer;

import com.team1.team1project.domain.Customer;
import com.team1.team1project.dto.CustomerDTO;
import com.team1.team1project.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream()
				.map(customer -> modelMapper.map(customer, CustomerDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<CustomerDTO> getCustomerById(int customerId) {
		return customerRepository.findById(customerId)
				.map(customer -> modelMapper.map(customer, CustomerDTO.class));
	}

	@Override
	public CustomerDTO createCustomer(CustomerDTO customerDTO) {
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		Customer saved = customerRepository.save(customer);
		return modelMapper.map(saved, CustomerDTO.class);
	}

	@Override
	public CustomerDTO updateCustomer(int customerId, CustomerDTO customerDTO) {
		if (customerRepository.existsById(customerId)) {
			Customer existing = customerRepository.findById(customerId).get();

			Customer customer = modelMapper.map(customerDTO, Customer.class);
			customer.setCustomerId(customerId);
			customer.setRegDate(existing.getRegDate());
			customer.setModDate(LocalDateTime.now());

			Customer updated = customerRepository.save(customer);
			return modelMapper.map(updated, CustomerDTO.class);
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
}




