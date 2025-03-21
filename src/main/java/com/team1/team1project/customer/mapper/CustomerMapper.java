package com.team1.team1project.customer.mapper;

import com.team1.team1project.customer.domain.Customer;
import com.team1.team1project.customer.dto.CustomerDTO;

public class CustomerMapper {

	// Entity -> DTO 변환
	public static CustomerDTO toDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(customer.getCustomerId());
		customerDTO.setCustomerName(customer.getCustomerName());
		customerDTO.setContactInfo(customer.getContactInfo());
		customerDTO.setAddress(customer.getAddress());
		return customerDTO;
	}

	// DTO -> Entity 변환
	public static Customer toEntity(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		customer.setCustomerId(customerDTO.getCustomerId());
		customer.setCustomerName(customerDTO.getCustomerName());
		customer.setContactInfo(customerDTO.getContactInfo());
		customer.setAddress(customerDTO.getAddress());
		return customer;
	}
}
