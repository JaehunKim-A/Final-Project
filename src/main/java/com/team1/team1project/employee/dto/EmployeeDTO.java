package com.team1.team1project.employee.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Integer employeeId;
    private String employeeName;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private LocalDate resignationDate;
    private String department;
    private String position;
    private BigDecimal salary;
    private String address;
    private String status;as
}
