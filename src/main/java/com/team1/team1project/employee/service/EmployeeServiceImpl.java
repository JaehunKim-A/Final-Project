package com.team1.team1project.employee.service;

import com.team1.team1project.employee.domain.Employee;
import com.team1.team1project.employee.dto.EmployeeDTO;
import com.team1.team1project.employee.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    /**
     * 모든 직원 조회
     * @return 직원 목록 리스트
     */
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * 특정 직원 조회
     * @param employeeId 조회할 직원의 ID
     * @return 직원 객체 (Optional)
     */
    @Override
    public Optional<EmployeeDTO> getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee .class);
        Employee saved = employeeRepository.save(employee);
        return modelMapper.map(saved, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updateEmployee(int employeeId, EmployeeDTO employeeDTO) {
        if (employeeRepository.existsById(employeeId)) {
            Employee existing = employeeRepository.findById(employeeId).get();

            Employee employee = modelMapper.map(employeeDTO, Employee.class);
            employee.setEmployeeId(employeeId);


            Employee updated = employeeRepository.save(employee);
            return modelMapper.map(updated, EmployeeDTO.class);
        }
        return null;
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

    @Override
    public EmployeeDTO getEmployeeByName(String employeeName) {
        Employee employee = employeeRepository.findByEmployeeName(employeeName);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public void updateEmployeeByName(Integer employeeId, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("사원 없음"));
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setHireDate(employeeDTO.getHireDate());
        employee.setResignationDate(employeeDTO.getResignationDate());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employee.setAddress(employeeDTO.getAddress());

        employeeRepository.save(employee);
    }



}
