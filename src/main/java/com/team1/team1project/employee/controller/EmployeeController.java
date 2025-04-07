package com.team1.team1project.employee.controller;

import com.team1.team1project.employee.domain.Employee;
import com.team1.team1project.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 직원 목록을 보여주는 메서드
     */
    @GetMapping("/table/employee")
    public String showEmployeeList(Model model,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @PageableDefault(size = 10) Pageable pageableRaw) {

        int pageIndex = page - 1;
        Pageable pageable = PageRequest.of(pageIndex, pageableRaw.getPageSize(), pageableRaw.getSort());
        Page<Employee> employeePage = employeeService.getEmployeePage(pageable);

        model.addAttribute("page", employeePage);
        model.addAttribute("pageNumber", page); // 현재 페이지 (1부터 시작)
        model.addAttribute("employees", employeePage.getContent());

        List<String> columnNames = List.of(
                "employeeId", "employeeName", "email", "phoneNumber", "hireDate", "resignationDate",
                "department", "position", "salary", "address", "status"
        );
        model.addAttribute("columns", columnNames);

        return "dist/employee/table";
    }

    /**
     * 직원 등록 폼을 표시하는 메서드
     */
    @GetMapping("/table/employee/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("employee", new Employee()); // 새 직원 객체를 모델에 추가
        return "dist/employee/register"; // 직원 등록 페이지로 이동
    }

    /**
     * 직원 등록 처리 메서드
     */
    @PostMapping("/table/employee/register")
    public String registerEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee); // 직원 등록
        return "redirect:/table/employee"; // 직원 목록 페이지로 리다이렉트
    }

    /**
     * 직원 수정 폼을 표시하는 메서드
     */
    @GetMapping("/table/employee/edit/{employeeId}")
    public String showEditForm(@PathVariable("employeeId") int employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id: " + employeeId));
        model.addAttribute("employee", employee); // 수정할 직원 정보 모델에 추가
        return "dist/employee/edit"; // 직원 수정 페이지로 이동
    }

    /**
     * 직원 수정 처리 메서드
     */
    @PostMapping("/table/employee/edit/{employeeId}")
    public String updateEmployee(@PathVariable("employeeId") int employeeId, @ModelAttribute Employee employee) {
        employeeService.updateEmployee(employeeId, employee); // 직원 정보 수정
        return "redirect:/table/employee"; // 직원 목록 페이지로 리다이렉트
    }

    /**
     * 직원 삭제 처리 메서드
     */
    @GetMapping("/table/employee/delete/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") int employeeId) {
        employeeService.deleteEmployee(employeeId); // 직원 삭제
        return "redirect:/table/employee"; // 직원 목록 페이지로 리다이렉트
    }
}
