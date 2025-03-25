package com.team1.team1project.finishedProductInbound.controller;

import com.team1.team1project.domain.FinishedProductInbound;
import com.team1.team1project.service.FinishedProductInboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/finished-product-inbounds")
@RequiredArgsConstructor
public class FinishedProductInboundController {

    private final FinishedProductInboundService service;

    @GetMapping
    public String showFinishedProductInbounds(Model model) {
        List<FinishedProductInbound> inbounds = service.getAllInbounds();
        model.addAttribute("inbounds", inbounds);
        return "FinishedProductInbound";  // 타임리프 템플릿 파일명
    }
}
