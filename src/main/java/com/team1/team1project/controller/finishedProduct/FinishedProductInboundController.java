package com.team1.team1project.controller.finishedProduct;

import com.team1.team1project.dto.FinishedProductInboundDTO;
import com.team1.team1project.service.finishedProduct.FinishedProductInboundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/finished-product")
public class FinishedProductInboundController {

    private final FinishedProductInboundService finishedProductInboundService;

    public FinishedProductInboundController(FinishedProductInboundService finishedProductInboundService) {
        this.finishedProductInboundService = finishedProductInboundService;
    }

    // 입고 목록 페이지를 반환하는 메소드
    @GetMapping("/inbound/list")
    public String getFinishedProductInbounds(Model model) {
        List<FinishedProductInboundDTO> finishedProductInbounds = finishedProductInboundService.getAllFinishedProductInbounds();
        model.addAttribute("finishedProductInbounds", finishedProductInbounds);
        return "FinishedProductInbound"; // 리스트 화면을 렌더링
    }

    // 입고 등록 처리
    @PostMapping("/inbound/register")
    public String createFinishedProductInbound(@ModelAttribute FinishedProductInboundDTO finishedProductInboundDTO) {
        finishedProductInboundService.createFinishedProductInbound(finishedProductInboundDTO);
        return "redirect:/finished-product/inbound/list"; // 입고 목록 페이지로 리디렉션
    }

    // 입고 수정 처리
    @PostMapping("/inbound/modify-status")
    public String modifyInboundStatus(@RequestParam Long inboundId, @RequestParam String status) {
        finishedProductInboundService.modifyInboundStatus(inboundId, status); // 상태 수정 처리
        return "redirect:/finished-product/inbound/list"; // 수정 후 목록 페이지로 리디렉션
    }

    // 입고 삭제 처리
    @DeleteMapping("/inbound/{inboundId}")
    @ResponseBody
    public String deleteFinishedProductInbound(@PathVariable Long inboundId) {
        finishedProductInboundService.deleteFinishedProductInbound(inboundId);
        return "삭제 완료!";
    }
}
