package com.team1.team1project.service;


import com.team1.team1project.employee.dto.EmployeeDTO;
import com.team1.team1project.employee.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloadService {

    private final EmployeeService employeeService;


    public Workbook createExcelByType(String type) {
        switch (type) {
            case "employee":
                return createEmployeeWorkbook();
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    private Workbook createEmployeeWorkbook() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        String[] headers = {"ID", "사원명", "이메일", "연락처", "입사일", "퇴사일", "부서", "직급", "급여", "주소"};

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");
        createHeaderRow(sheet, headers);

        int rowNum = 1;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (EmployeeDTO c : employees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(c.getEmployeeId());
            row.createCell(1).setCellValue(c.getEmployeeName());
            row.createCell(2).setCellValue(c.getEmail());
            row.createCell(3).setCellValue(c.getPhoneNumber());
            row.createCell(4).setCellValue(c.getHireDate() != null ? c.getHireDate().format(fmt) : ""); // ✅ 수정
            row.createCell(5).setCellValue(c.getResignationDate() != null ? c.getResignationDate().format(fmt) : ""); // ✅ 수정
            row.createCell(6).setCellValue(c.getDepartment());
            row.createCell(7).setCellValue(c.getPosition());
            row.createCell(8).setCellValue(c.getSalary().doubleValue());
            row.createCell(9).setCellValue(c.getAddress());
        }

        autoSizeColumns(sheet, headers.length);
        return workbook;
    }



    public String createCsvByType(String type) {
        switch (type) {
            case "employee":
                return createEmployeeCsv();
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    private String createEmployeeCsv() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        StringBuilder sb = new StringBuilder();
        sb.append("ID|사원명|이메일|연락처|입사일|퇴사일|부서|직급|급여|주소\n");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (EmployeeDTO c : employees) {
            sb.append(c.getEmployeeId()).append("|")
                    .append(c.getEmployeeName()).append("|")
                    .append(c.getEmail()).append("|")
                    .append(c.getPhoneNumber()).append("|")
                    .append(c.getHireDate() != null ? c.getHireDate().format(fmt) : "").append("|") // ✅ 수정
                    .append(c.getResignationDate() != null ? c.getResignationDate().format(fmt) : "").append("|") // ✅ 수정
                    .append(c.getDepartment()).append("|")
                    .append(c.getPosition()).append("|")
                    .append(c.getSalary()).append("|")
                    .append(c.getAddress()).append("|n");
        }
        return sb.toString();
    }



    private void createHeaderRow(Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    }

    private void autoSizeColumns(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}