package com.team1.team1project.customer.controller;

import com.team1.team1project.customer.domain.Customer;
import com.team1.team1project.customer.repository.CustomerRepository;
import com.team1.team1project.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private EntityManager entityManager;

	@GetMapping("/table/customer")
	public String showCustomerList(Model model) {
		// 컬럼명을 직접 순서대로 설정
		List<String> columnNames = new ArrayList<>();
		columnNames.add("customerId");
		columnNames.add("customerName");
		columnNames.add("address");
		columnNames.add("contactInfo");
		columnNames.add("reg_date");
		columnNames.add("mod_date");

		// 고객 목록 가져오기
		model.addAttribute("customers", customerService.getAllCustomers());

		// 컬럼명 정보를 모델에 전달
		model.addAttribute("columns", columnNames);

		return "dist/customer/table"; // 고객 목록을 표시할 HTML 템플릿
	}


	// JPA를 통해 엔티티의 컬럼명 가져오기
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<>();
		EntityType<Customer> entityType = entityManager.getMetamodel().entity(Customer.class);

		// 엔티티의 속성 이름을 가져와서 컬럼명으로 사용
		for (SingularAttribute<? super Customer, ?> attribute : entityType.getSingularAttributes()) {
			columnNames.add(attribute.getName());
		}

		return columnNames;
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll(); // findAll() 메서드가 데이터를 제대로 반환하는지 확인
	}

}
