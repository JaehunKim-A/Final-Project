package com.team1.team1project.service;

import com.team1.team1project.domain.Customer;
import com.team1.team1project.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceTests {
	@Autowired
	private CustomerService customerService;

	@Test
	public void testModify() {
		Customer customer = new Customer(7, "ModifiedName", "Modified123-456-7890", "ModifiedAddress");

		Customer result = customerService.updateCustomer(7, customer);
	}


}
