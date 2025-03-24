package com.team1.team1project.rawmaterialoutbound.controller;

import com.team1.team1project.domain.RawMaterialInbound;
import com.team1.team1project.domain.RawMaterialOutbound;
import com.team1.team1project.rawmaterialoutbound.service.RawMaterialOutboundService;
import com.team1.team1project.service.RawMaterialInboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RawMaterialOutboundController {

    private final RawMaterialOutboundService rawMaterialOutboundService;

    // 원자재 입고 목록을 보여주는 메서드
    @GetMapping("/raw-material-outbounds")
    public String showRawMaterialOutbounds(Model model) {
        // 원자재 입고 데이터를 서비스에서 가져옴
        List<RawMaterialOutbound> outbounds = rawMaterialOutboundService.getAllOutbounds();

        // 데이터를 모델에 담아서 뷰로 전달
        model.addAttribute("outbounds", outbounds);

        return "RawMaterialOutbound";  // 타임리프 템플릿 이름
    }
}
