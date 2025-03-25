package com.team1.team1project.employee.service;

import com.team1.team1project.employee.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    // 모든 직원 조회
    List<Employee> getAllEmployees();

    // 특정 직원 조회
    Optional<Employee> getEmployeeById(int employeeId);

    // 직원 등록
    Employee createEmployee(Employee employee);

    // 직원 수정
    Employee updateEmployee(int employeeId, Employee updatedEmployee);

    // 직원 삭제
    boolean deleteEmployee(int employeeId);

    // ✅ 페이지네이션용 메서드
    Page<Employee> getEmployeePage(Pageable pageable);


    void saveEmployee(Employee employee);
}
