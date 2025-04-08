package com.team1.team1project.service;


import com.team1.team1project.employee.dto.EmployeeDTO;
import com.team1.team1project.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final EmployeeService employeeService;


    public void processEmployeeCsv(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirst = true;

            while ((line = reader.readLine()) != null) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }

                if (line.trim().isEmpty()) continue;

                String[] fields = line.split("\\|");
                if (fields.length < 3) {
                    System.err.println("잘못된 사원 CSV 라인 (필드 부족): " + line);
                    continue;
                }

                String employeeName = fields[0].trim();
                String email = fields[1].trim();
                String phoneNumber = fields[2].trim();
                LocalDate hireDate = fields[3].trim().isEmpty() ? null : LocalDate.parse(fields[3].trim());
                LocalDate resignationDate = fields[4].trim().isEmpty() ? null : LocalDate.parse(fields[4].trim());

                String department = fields[5].trim();
                String position = fields[6].trim();
                BigDecimal salary = new BigDecimal(fields[7].trim());
                String address = fields[8].trim();

                EmployeeDTO existing = employeeService.getEmployeeByName(employeeName);
                if (existing != null) {
                    existing.setEmail(email);
                    existing.setPhoneNumber(phoneNumber);
                    existing.setHireDate(hireDate);
                    existing.setResignationDate(resignationDate);
                    existing.setDepartment(department);
                    existing.setPosition(position);
                    existing.setSalary(salary);
                    existing.setAddress(address);
                    employeeService.updateEmployee(existing.getEmployeeId(), existing);
                } else {
                    System.out.println("등록되지 않은 사원 이름: " + employeeName);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("사원 CSV 처리 중 오류 발생", e);
        }

    }


}