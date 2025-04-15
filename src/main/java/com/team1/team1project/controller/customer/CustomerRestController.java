package com.team1.team1project.controller.customer;

import com.team1.team1project.dto.CustomerDTO;
import com.team1.team1project.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerRestController {

	private final CustomerService customerService;

	@GetMapping("/all")
	public List<CustomerDTO> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	@PostMapping
	public ResponseEntity<Void> registerCustomer(@RequestBody CustomerDTO dto) {
		customerService.createCustomer(dto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO dto) {
		customerService.updateCustomer(id, dto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.ok().build();
	}
}
