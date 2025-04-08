package com.team1.team1project.employee.service;


import com.team1.team1project.employee.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    Optional<EmployeeDTO> getEmployeeById(int employeeId);
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(int employeeId, EmployeeDTO employeeDTO);
    boolean deleteEmployee(int employeeId);
    EmployeeDTO getEmployeeByName(String employeeName);
    void updateEmployeeByName(Integer employeeId, EmployeeDTO employeeDTO);
}