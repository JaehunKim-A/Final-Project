package com.team1.team1project.controller.customer;

import com.team1.team1project.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/table/customerOrders")
@RequiredArgsConstructor
@Log4j2
public class CustomerOrdersController {

	private final CustomerService customerService;

	@GetMapping({"", "/"})
	public String showCustomerOrdersTable(Model model) {
		model.addAttribute("customers", customerService.getAllCustomers());
		return "customerOrders/table";
	}
}
