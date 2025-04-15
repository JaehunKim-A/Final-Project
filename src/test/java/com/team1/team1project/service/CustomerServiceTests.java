package com.team1.team1project.service;

import com.team1.team1project.service.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceTests {
	@Autowired
	private CustomerService customerService;

	@Test
	public void testModify() {
	}
}