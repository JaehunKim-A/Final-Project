package com.team1.team1project.customer.controller;

import com.team1.team1project.customer.service.CustomerService;
import com.team1.team1project.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/table/customer")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping({"", "/"})
	public String showCustomerList(Model model) {
		List<CustomerDTO> customers = customerService.getAllCustomers();

		model.addAttribute("customers", customers);

		return "customer/table";
	}

	// 등록 폼
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("customer", new CustomerDTO());
		return "customer/register";
	}

	// 등록 처리
	@PostMapping("/register")
	public String registerCustomer(@ModelAttribute("customer") CustomerDTO customerDTO) {
		customerService.createCustomer(customerDTO);
		return "redirect:/table/customer";
	}

	// 수정 폼
	@GetMapping("/edit/{customerId}")
	public String showEditForm(@PathVariable("customerId") int customerId, Model model) {
		CustomerDTO customerDTO = customerService.getCustomerById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid customer Id: " + customerId));
		model.addAttribute("customer", customerDTO);
		return "customer/edit";
	}

	// 수정 처리
	@PostMapping("/edit/{customerId}")
	public String updateCustomer(@PathVariable("customerId") int customerId,
	                             @ModelAttribute("customer") CustomerDTO customerDTO) {
		customerService.updateCustomer(customerId, customerDTO);
		return "redirect:/table/customer";
	}

	// 삭제
	@GetMapping("/delete/{customerId}")
	public String deleteCustomer(@PathVariable("customerId") int customerId) {
		customerService.deleteCustomer(customerId);
		return "redirect:/table/customer";
	}
}
