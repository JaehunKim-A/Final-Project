package com.team1.team1project.productProcessManagement.controller;

import com.team1.team1project.productProcessManagement.service.ProductProcessManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/productProcessManagement")
@AllArgsConstructor
public class ProductProcessManagementController {

    private final ProductProcessManagementService productProcessManagementService;

    @GetMapping("/productProcessManagement")
    public String productProcessManagementPageGet(@RequestParam(required = false) Integer year, Model model) {


        return "productProcessManagement/productProcessManagement";
    }

}