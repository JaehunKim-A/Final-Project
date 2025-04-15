package com.team1.team1project.controller.customer;

import com.team1.team1project.dto.CustomerOrdersDTO;
import com.team1.team1project.service.customer.CustomerOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/customerOrders")
public class CustomerOrdersRestController {

	private final CustomerOrdersService customerOrdersService;

	@GetMapping("/all")
	public List<CustomerOrdersDTO> getAllCustomerOrders() {
		return customerOrdersService.getAllCustomerOrders();
	}

	@PostMapping
	public ResponseEntity<Void> registerCustomerOrder(@RequestBody CustomerOrdersDTO dto) {
		customerOrdersService.createCustomerOrder(dto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCustomerOrder(@PathVariable int id, @RequestBody CustomerOrdersDTO dto) {
		customerOrdersService.updateCustomerOrder(id, dto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomerOrder(@PathVariable int id) {
		customerOrdersService.deleteCustomerOrder(id);
		return ResponseEntity.ok().build();
	}
}
