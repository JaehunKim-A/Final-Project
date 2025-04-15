package com.team1.team1project.controller;

import com.team1.team1project.domain.Employee;
import com.team1.team1project.dto.EmployeeDTO;
import com.team1.team1project.security.CustomUserDetails;
import com.team1.team1project.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {
    private final EmployeeService employeeService;

    @Autowired
    public LoginController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 로그인 페이지 표시
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html 템플릿으로 연결
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {
            int employeeId = userDetails.getEmployeeId();

            // 직원 정보 조회
            Optional<EmployeeDTO> employeeOpt = employeeService.getEmployeeById(employeeId);

            if (employeeOpt.isPresent()) {
                EmployeeDTO employee = employeeOpt.get();

                // 사용자 정보를 모델에 추가
                model.addAttribute("employeeName", employee.getEmployeeName());
                model.addAttribute("employeeId", employee.getEmployeeId());
                model.addAttribute("department", employee.getDepartment());
                model.addAttribute("position", employee.getPosition());

                // 같은 부서의 직원 목록 가져오기
                List<EmployeeDTO> departmentEmployees = employeeService.findByDepartment(employee.getDepartment());
                // 현재 로그인한 사용자를 목록에서 제외 (선택사항)
                departmentEmployees.removeIf(emp -> emp.getEmployeeId() == employee.getEmployeeId());

                model.addAttribute("departmentEmployees", departmentEmployees);
            } else {
                // 기존 코드 유지 (사용자 ID만 추가)
                model.addAttribute("employeeName", userDetails.getEmployeeName());
                model.addAttribute("employeeId", userDetails.getEmployeeId());
            }
        }
        return "dashboard"; // dashboard.html 템플릿으로 연결
    }

    /**
     * 인덱스 페이지 (루트 경로)
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard"; // 루트 경로 접속 시 대시보드로 리다이렉트
    }
}