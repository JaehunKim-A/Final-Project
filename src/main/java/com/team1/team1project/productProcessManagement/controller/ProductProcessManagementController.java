package com.team1.team1project.productProcessManagement.controller;

import com.team1.team1project.productProcessManagement.dto.MachineHistoryYearDTO;
import com.team1.team1project.productProcessManagement.service.ProductProcessManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/productProcessManagement")
@AllArgsConstructor
public class ProductProcessManagementController {

    private final ProductProcessManagementService productProcessManagementService;

    @GetMapping("/productProcessManagement")
    public String productProcessManagementPageGet(@RequestParam(required = false) Integer year, Model model) {
//        // 연도 파라미터가 없으면 현재 연도 사용
//        if (year == null) {
//            year = Calendar.getInstance().get(Calendar.YEAR);
//        }
//
//        // 데이터 가져오기
//        MachineHistoryYearDTO machineHistoryYearDTO = productProcessManagementService.getMachineHistoryByYear(year);
//
//        // 컬럼 이름 설정
//        List<String> columns = Arrays.asList("History ID", "Machine ID", "Production Amount",
//                "Defective Amount", "Reg Date", "Mod Date");
//
//        // 모델에 데이터 추가
//        model.addAttribute("machineHistoryYearDTO", machineHistoryYearDTO);
//        model.addAttribute("columns", columns);

        return "productProcessManagement/productProcessManagement";
    }

    @PostMapping("/productProcessManagement")
    public String productProcessManagementPagePost(@RequestParam Integer year, Model model) {

        MachineHistoryYearDTO machineHistoryYearDTO =
                productProcessManagementService.getMachineHistoryByYear(year);

        List<String> columns = Arrays.asList("History ID", "Machine ID", "Production Amount",
                "Defective Amount", "Reg Date", "Mod Date");

        // 모델에 데이터 추가
        model.addAttribute("machineHistoryYearDTO", machineHistoryYearDTO);
        model.addAttribute("columns", columns);

        return "productProcessManagement/productProcessManagement";
    }
}