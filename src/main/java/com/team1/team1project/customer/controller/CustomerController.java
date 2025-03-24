package com.team1.team1project.customer.controller;

import com.team1.team1project.controller.PageController;
import com.team1.team1project.customer.domain.Customer;
import com.team1.team1project.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class CustomerController extends PageController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/table/customer")
	public String showCustomerList(Model model,
	                               @RequestParam(value = "page", defaultValue = "1") int page,
	                               Pageable pageableRaw) {

		Pageable pageable = getPageable(page, pageableRaw);  // PageController에서 상속받은 메서드 사용
		Page<Customer> customerPage = customerService.getCustomerPage(pageable);

		List<String> columnNames = List.of(
				"customerId", "customerName", "address", "contactInfo", "reg_date", "mod_date"
		);
		addPagination(model, customerPage, page, columnNames);  // PageController에서 상속받은 메서드 사용

		return "dist/customer/table";
	}

	// 고객 등록 폼을 표시하는 메서드
	@GetMapping("/table/customer/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("customer", new Customer()); // 새 고객 객체를 모델에 추가
		return "dist/customer/register"; // 고객 등록 페이지로 이동
	}

	// 고객 등록 처리 메서드
	@PostMapping("/table/customer/register")
	public String registerCustomer(@ModelAttribute Customer customer) {
		customerService.createCustomer(customer); // 고객 등록
		return "redirect:/table/customer"; // 고객 목록 페이지로 리다이렉트
	}

	// 고객 수정 폼을 표시하는 메서드
	@GetMapping("/table/customer/edit/{customerId}")
	public String showEditForm(@PathVariable("customerId") int customerId, Model model) {
		Customer customer = customerService.getCustomerById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + customerId));
		model.addAttribute("customer", customer); // 수정할 고객 정보 모델에 추가
		return "dist/customer/edit"; // 고객 수정 페이지로 이동
	}

	// 고객 수정 처리 메서드
	@PostMapping("/table/customer/edit/{customerId}")
	public String updateCustomer(@PathVariable("customerId") int customerId, @ModelAttribute Customer customer) {
		customerService.updateCustomer(customerId, customer); // 고객 정보 수정
		return "redirect:/table/customer"; // 고객 목록 페이지로 리다이렉트
	}

	// 고객 삭제 처리 메서드
	@GetMapping("/table/customer/delete/{customerId}")
	public String deleteCustomer(@PathVariable("customerId") int customerId) {
		customerService.deleteCustomer(customerId); // 고객 삭제
		return "redirect:/table/customer"; // 고객 목록 페이지로 리다이렉트
	}

}
