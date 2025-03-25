package com.team1.team1project.finishedProductOutbound.controller;

import com.team1.team1project.domain.FinishedProductOutbound;
import com.team1.team1project.finishedProductOutbound.service.FinishedProductOutboundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FinishedProductOutboundController {

    private final FinishedProductOutboundService outboundService;

    public FinishedProductOutboundController(FinishedProductOutboundService outboundService) {
        this.outboundService = outboundService;
    }

    @GetMapping("/finished-product-outbounds")
    public String showOutboundList(Model model) {
        List<FinishedProductOutbound> outbounds = outboundService.getAllOutbounds();
        model.addAttribute("outbounds", outbounds);
        return "FinishedProductOutbound"; // HTML 파일 이름 (Thymeleaf 사용 시 .html 필요 없음)
    }
}