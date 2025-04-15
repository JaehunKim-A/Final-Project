package com.team1.team1project.service;

import com.team1.team1project.domain.Employee;
import com.team1.team1project.domain.Login;
import com.team1.team1project.repository.EmployeeRepository;
import com.team1.team1project.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(LoginRepository loginRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Login registerNewLogin(String loginId, String rawPassword, int employeeId) {
        // 직원 정보 조회
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("직원 ID가 유효하지 않습니다: " + employeeId));

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 로그인 계정 생성
        Login login = Login.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .employee(employee)
                .build();

        return loginRepository.save(login);
    }

    public boolean validatePassword(String loginId, String rawPassword) {
        Login login = loginRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + loginId));

        return passwordEncoder.matches(rawPassword, login.getPassword());
    }
}