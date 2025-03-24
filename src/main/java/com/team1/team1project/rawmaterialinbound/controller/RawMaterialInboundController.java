package com.team1.team1project.controller;

import com.team1.team1project.domain.RawMaterialInbound;
import com.team1.team1project.service.RawMaterialInboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RawMaterialInboundController {

    private final RawMaterialInboundService rawMaterialInboundService;

    // 원자재 입고 목록을 보여주는 메서드
    @GetMapping("/raw-material-inbounds")
    public String showRawMaterialInbounds(Model model) {
        // 원자재 입고 데이터를 서비스에서 가져옴
        List<RawMaterialInbound> inbounds = rawMaterialInboundService.getAllInbounds();

        // 데이터를 모델에 담아서 뷰로 전달
        model.addAttribute("inbounds", inbounds);

        // "raw-material-inbounds" 템플릿 이름을 반환
        return "RawMaterialInbound";  // 타임리프 템플릿 이름
    }
}
