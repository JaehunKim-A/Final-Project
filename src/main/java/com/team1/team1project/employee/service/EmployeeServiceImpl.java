package com.team1.team1project.employee.service;

import com.team1.team1project.employee.domain.Employee;
import com.team1.team1project.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

   @Autowired
   private EmployeeRepository employeeRepository;

    /**
     * 모든 직원 조회
     * @return 직원 목록 리스트
     */
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * 특정 직원 조회
     * @param employeeId 조회할 직원의 ID
     * @return 직원 객체 (Optional)
     */
    @Override
    public Optional<Employee> getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId);
    }

    /**
     * 직원 등록
     * @param employee 등록할 직원 객체
     * @return 저장된 직원 객체
     */
    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * 직원 정보 수정
     * @param employeeId 수정할 직원의 ID
     * @param updatedEmployee 수정된 직원 객체
     * @return 수정된 직원 객체 (없으면 null 반환)
     */
    @Override
    public Employee updateEmployee(int employeeId, Employee employee) {
        if (employeeRepository.existsById(employeeId)) {
            employee.setEmployeeId(employeeId); // 기존 ID 유지
            return employeeRepository.save(employee); // 업데이트 후 저장
        }
        return null; // 존재하지 않는 경우 null 반환
    }


    /**
     * 직원 삭제
     * @param employeeId 삭제할 직원의 ID
     * @return 삭제 성공 여부
     */
    @Override
    public boolean deleteEmployee(int employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return true; // 삭제 성공
        }
        return false; // 직원이 존재하지 않음
    }

    /**
     * 직원 목록 (페이지네이션 지원)
     * @param pageable 페이지 정보
     * @return 직원 페이지 객체
     */
    @Override
    public Page<Employee> getEmployeePage(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public void saveEmployee(Employee employee) {

    }


}
