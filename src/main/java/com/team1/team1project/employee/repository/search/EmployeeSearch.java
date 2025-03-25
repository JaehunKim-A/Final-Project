package com.team1.team1project.employee.repository.search;

import com.team1.team1project.employee.domain.Employee;

import java.util.List;

public interface EmployeeSearch {
    List<Employee> searchEmployees(String keyword);
}
