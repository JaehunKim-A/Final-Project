package com.team1.team1project.controller;

import com.team1.team1project.domain.Employee;
import com.team1.team1project.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * 로그인 페이지 표시
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html 템플릿으로 연결
    }

    /**
     * 대시보드 페이지 표시
     */
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {

            // 로그인한 사용자의 employeeName을 모델에 추가
            model.addAttribute("employeeName", userDetails.getEmployeeName());
            model.addAttribute("employeeId", userDetails.getEmployeeId());
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