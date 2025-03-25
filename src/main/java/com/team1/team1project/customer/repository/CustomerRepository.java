package com.team1.team1project.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team1.team1project.domain.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
