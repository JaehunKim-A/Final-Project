package com.team1.team1project.employee.domain;


import com.team1.team1project.domain.BaseEntity;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "employee_name", nullable = false, length = 255)
    private String employeeName;

    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "hire_date")
    private LocalDate hireDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "resignation_date")
    private LocalDate resignationDate;

    @Column(name = "department", length = 255)
    private String department;

    @Column(name = "position", length = 255)
    private String position;

    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;


    // reg_date와 mod_date는 LocalDateTime으로 저장
    @Override
    public LocalDateTime getReg_date() {
        return super.getRegDate();
    }

    @Override
    public LocalDateTime getMod_date() {
        return super.getModDate();
    }

}

