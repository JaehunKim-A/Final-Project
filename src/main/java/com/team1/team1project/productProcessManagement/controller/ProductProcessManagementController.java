package com.team1.team1project.productProcessManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productProcessManagement")
public class ProductProcessManagementController {

    @GetMapping("/productProcessManagement")
    public String testPage() {
        return "productProcessManagement/productProcessManagement";
    }
}
