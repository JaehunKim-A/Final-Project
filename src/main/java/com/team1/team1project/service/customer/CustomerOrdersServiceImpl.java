package com.team1.team1project.service.customer;

import com.team1.team1project.repository.CustomerOrdersRepository;
import com.team1.team1project.domain.CustomerOrders;
import com.team1.team1project.dto.CustomerOrdersDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerOrdersServiceImpl implements CustomerOrdersService{

	private final CustomerOrdersRepository customerOrdersRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<CustomerOrdersDTO> getAllCustomerOrders() {
		return customerOrdersRepository.findAll().stream()
				.map(customerOrders -> modelMapper.map(customerOrders, CustomerOrdersDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<CustomerOrdersDTO> getCustomerOrderById(int orderId) {
		return customerOrdersRepository.findById(orderId)
				.map(customerOrders -> modelMapper.map(customerOrders, CustomerOrdersDTO.class));
	}

	@Override
	public CustomerOrdersDTO createCustomerOrder(CustomerOrdersDTO customerOrdersDTO) {
		CustomerOrders customerOrders = modelMapper.map(customerOrdersDTO, CustomerOrders.class);
		CustomerOrders saved = customerOrdersRepository.save(customerOrders);
		return modelMapper.map(saved, CustomerOrdersDTO.class);
	}

	@Override
	public CustomerOrdersDTO updateCustomerOrder(int orderId, CustomerOrdersDTO customerOrdersDTO) {
		if (customerOrdersRepository.existsById(orderId)) {
			CustomerOrders existing = customerOrdersRepository.findById(orderId).get();

			CustomerOrders customerOrders = modelMapper.map(customerOrdersDTO, CustomerOrders.class);
			customerOrders.setOrderId(orderId);
			customerOrders.setRegDate(existing.getRegDate());
			customerOrders.setModDate(LocalDateTime.now());

			CustomerOrders updated = customerOrdersRepository.save(customerOrders);
			return modelMapper.map(updated, CustomerOrdersDTO.class);
		}
		return null;
	}

	@Override
	public boolean deleteCustomerOrder(int orderId) {
		if (customerOrdersRepository.existsById(orderId)) {
			customerOrdersRepository.deleteById(orderId);
			return true;
		}
		return false;
	}

}
