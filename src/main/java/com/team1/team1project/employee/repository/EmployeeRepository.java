package com.team1.team1project.employee.repository;

import com.team1.team1project.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
            Employee findByEmployeeName(String employeeName);
}