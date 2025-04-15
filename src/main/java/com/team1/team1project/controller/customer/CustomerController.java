package com.team1.team1project.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/table/customer")
public class CustomerController {

	@GetMapping({"", "/"})
	public String showCustomerTablePage(Model model) {
		return "customer/table";
	}
}
