package com.team1.team1project.repository;

import com.team1.team1project.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByEmployeeName(String employeeName);
    List<Employee> findByDepartment(String department);
}