package com.team1.team1project.finishedProductOutbound.controller;

import com.team1.team1project.dto.FinishedProductOutboundDTO;
import com.team1.team1project.finishedProductOutbound.service.FinishedProductOutboundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/finished-product")
public class FinishedProductOutboundController {

    private final FinishedProductOutboundService finishedProductOutboundService;

    public FinishedProductOutboundController(FinishedProductOutboundService finishedProductOutboundService) {
        this.finishedProductOutboundService = finishedProductOutboundService;
    }

    // 출고 목록 페이지를 반환하는 메소드
    @GetMapping("/outbound/list")
    public String getFinishedProductOutbounds(Model model) {
        List<FinishedProductOutboundDTO> finishedProductOutbounds = finishedProductOutboundService.getAllFinishedProductOutbounds();
        model.addAttribute("finishedProductOutbounds", finishedProductOutbounds);
        return "FinishedProductOutbound"; // 리스트 화면을 렌더링
    }

    // 출고 등록 처리
    @PostMapping("/outbound/register")
    public String createFinishedProductOutbound(@ModelAttribute FinishedProductOutboundDTO finishedProductOutboundDTO) {
        finishedProductOutboundService.createFinishedProductOutbound(finishedProductOutboundDTO);
        return "redirect:finished-product/outbound/list"; // 입고 목록 페이지로 리디렉션
    }

    // 출고 수정 처리
    @PostMapping("/outbound/modify-status")
    public String modifyOutboundStatus(@RequestParam Long outboundId, @RequestParam String status) {
        finishedProductOutboundService.modifyOutboundStatus(outboundId, status); // 상태 수정 처리
        return "redirect:/finished-product/outbound/list"; // 수정 후 목록 페이지로 리디렉션
    }

    // 출고 삭제 처리
    @DeleteMapping("/outbound/{outboundId}")
    @ResponseBody
    public String deleteFinishedProductOutbound(@PathVariable Long outboundId) {
        finishedProductOutboundService.deleteFinishedProductOutbound(outboundId);
        return "삭제 완료!";
    }
}
