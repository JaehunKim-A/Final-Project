package com.team1.team1project.customerOrders.repository;

import com.team1.team1project.domain.CustomerOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrdersRepository extends JpaRepository<CustomerOrders, Integer> {

}
