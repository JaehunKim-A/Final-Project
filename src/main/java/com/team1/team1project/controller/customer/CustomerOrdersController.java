package com.team1.team1project.controller.customer;

import com.team1.team1project.dto.CustomerDTO;
import com.team1.team1project.service.customer.CustomerService;
import com.team1.team1project.service.customer.CustomerOrdersService;
import com.team1.team1project.dto.CustomerOrdersDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/table/customerOrders")
@RequiredArgsConstructor
public class CustomerOrdersController {
	private final CustomerOrdersService customerOrdersService;
	private final CustomerService customerService;

	@GetMapping({"", "/"})
	public String showCustomerOrdersList(Model model) {
		List<CustomerOrdersDTO> customerOrders = customerOrdersService.getAllCustomerOrders();
		List<CustomerDTO> customers = customerService.getAllCustomers();

		model.addAttribute("customerOrders", customerOrders);
		model.addAttribute("customers", customers);

		return "customerOrders/table";
	}

	// 등록 폼
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("customerOrders", new CustomerOrdersDTO());
		return "customerOrders/register";
	}

	// 등록 처리
	@PostMapping("/register")
	public String registerCustomer(@ModelAttribute("customerOrders") CustomerOrdersDTO customerOrdersDTO) {
		customerOrdersService.createCustomerOrder(customerOrdersDTO);
		return "redirect:/table/customerOrders";
	}

	// 수정 폼
	@GetMapping("/edit/{orderId}")
	public String showEditForm(@PathVariable("orderId") int orderId, Model model) {
		CustomerOrdersDTO customerOrdersDTO = customerOrdersService.getCustomerOrderById(orderId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid customerOrder Id: " + orderId));
		model.addAttribute("customerOrders", customerOrdersDTO);
		return "customerOrders/edit";
	}

	// 수정 처리
	@PostMapping("/edit/{orderId}")
	public String updateCustomer(@PathVariable("orderId") int orderId,
	                             @ModelAttribute("customerOrders") CustomerOrdersDTO customerOrdersDTO) {
		customerOrdersService.updateCustomerOrder(orderId, customerOrdersDTO);
		return "redirect:/table/customerOrders";
	}

	// 삭제
	@GetMapping("/delete/{orderId}")
	public String deleteCustomer(@PathVariable("orderId") int orderId) {
		customerOrdersService.deleteCustomerOrder(orderId);
		return "redirect:/table/customerOrders";
	}

}
