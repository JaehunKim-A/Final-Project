package com.team1.team1project.customerOrders.service;

import com.team1.team1project.dto.CustomerOrdersDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerOrdersService {
	List<CustomerOrdersDTO> getAllCustomerOrders();
	Optional<CustomerOrdersDTO> getCustomerOrderById(int orderId);
	CustomerOrdersDTO createCustomerOrder(CustomerOrdersDTO customerOrdersDTO);
	CustomerOrdersDTO updateCustomerOrder(int orderId, CustomerOrdersDTO customerOrdersDTO);
	boolean deleteCustomerOrder(int orderId);
}
