package com.team1.team1project.employee.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private int employeeId;
    private String employeeName;
    private String email;
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate resignationDate;
    private String department;
    private String position;
    private BigDecimal salary;
    private String address;
}
