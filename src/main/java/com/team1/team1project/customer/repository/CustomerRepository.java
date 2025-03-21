package com.team1.team1project.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team1.team1project.customer.domain.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	// 기본적인 CRUD 메서드는 JpaRepository에서 자동으로 제공됨.

	// 예시: 고객 이름으로 검색하는 메서드 추가
	// List<Customer> findByCustomerName(String customerName);
}
