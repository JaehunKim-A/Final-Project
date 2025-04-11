package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "logins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", unique = true, nullable = false, length = 100)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    // employeeId 기반 연관 설정
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // employeeName은 Employee에서 동적으로 가져옴
    public String getEmployeeName() {
        return employee != null ? employee.getEmployeeName() : null;
    }
}
