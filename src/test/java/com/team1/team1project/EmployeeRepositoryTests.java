package com.team1.team1project;

import com.team1.team1project.employee.domain.Employee;
import com.team1.team1project.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final String[] DEPARTMENTS = {"영업팀", "인사팀", "구매/자재팀"};
    private static final String[] POSITIONS = {"팀장", "대리", "대리", "사원", "사원"};

    private static final String[] LAST_NAMES = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임"};
    private static final String[] FIRST_NAMES = {"민수", "지연", "도윤", "서현", "예준", "하윤", "준호", "지호", "수빈", "현우"};

    private Random random = new Random();

    private String getRandomName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)] + FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    private String getRandomEmail(String name) {
        return name.toLowerCase() + UUID.randomUUID().toString().substring(0, 5) + "@example.com";
    }

    private String getRandomPhoneNumber() {
        return "010-" + (random.nextInt(9000) + 1000) + "-" + (random.nextInt(9000) + 1000);
    }

    private LocalDate getRandomHireDate() {
        return LocalDate.of(random.nextInt(5) + 2019, random.nextInt(12) + 1, random.nextInt(28) + 1);
    }

    private BigDecimal getRandomSalary() {
        return new BigDecimal(3000 + random.nextInt(5000)); // 3000 ~ 8000 사이의 랜덤 값
    }

    private String getRandomAddress() {
        return (random.nextInt(1000) + 1) + " Main Street, City";
    }

    @Test
    public void testSaveAndFindEmployee() {
        // Given - 각 부서별 5명씩 생성
        List<Employee> employees = IntStream.range(0, DEPARTMENTS.length * 5)
                .mapToObj(i -> {
                    String department = DEPARTMENTS[i / 5];
                    String position = POSITIONS[i % 5];
                    String name = getRandomName();
                    return Employee.builder()
                            .employeeName(name)
                            .email(getRandomEmail(name))
                            .phoneNumber(getRandomPhoneNumber())
                            .hireDate(getRandomHireDate())
                            .department(department)
                            .position(position)
                            .salary(getRandomSalary())
                            .address(getRandomAddress())
                            .status("Active")
                            .build();
                })
                .collect(Collectors.toList());

        // When
        employeeRepository.saveAll(employees);

        // Then
        List<Employee> retrievedEmployees = employeeRepository.findAll();
        assertThat(retrievedEmployees).hasSize(DEPARTMENTS.length * 5);

        // Check that regdate and moddate are not null
        retrievedEmployees.forEach(employee -> {
            assertThat(employee.getReg_date()).isNotNull();
            assertThat(employee.getMod_date()).isNotNull();
        });

        // 부서별 팀장 1명, 대리 2명, 사원 2명 확인
        for (String department : DEPARTMENTS) {
            List<Employee> departmentEmployees = retrievedEmployees.stream()
                    .filter(e -> e.getDepartment().equals(department))
                    .collect(Collectors.toList());

            assertThat(departmentEmployees).hasSize(5);
            assertThat(departmentEmployees.stream().filter(e -> e.getPosition().equals("팀장")).count()).isEqualTo(1);
            assertThat(departmentEmployees.stream().filter(e -> e.getPosition().equals("대리")).count()).isEqualTo(2);
            assertThat(departmentEmployees.stream().filter(e -> e.getPosition().equals("사원")).count()).isEqualTo(2);
        }
    }
}

