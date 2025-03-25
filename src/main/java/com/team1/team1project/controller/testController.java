package com.team1.team1project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @GetMapping("/test")
    public String testPage() {
        return "dist/index";
    }

    @GetMapping("/table")
    public String tabletestPage() {
        return "dist/table";
    }
}
