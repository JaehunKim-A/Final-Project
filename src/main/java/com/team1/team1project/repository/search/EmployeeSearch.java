package com.team1.team1project.repository.search;


import com.team1.team1project.domain.Employee;

import java.util.List;

public interface EmployeeSearch {
    List<Employee> searchEmployees(String keyword);
}
