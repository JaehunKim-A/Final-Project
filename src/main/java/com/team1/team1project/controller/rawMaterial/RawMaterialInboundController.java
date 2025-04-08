package com.team1.team1project.controller.rawMaterial;

import com.team1.team1project.domain.RawMaterialInbound;
import com.team1.team1project.service.rawMaterial.RawMaterialInboundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/raw-material/inbound")
public class RawMaterialInboundController {

    private final RawMaterialInboundService rawMaterialInboundService;

    public RawMaterialInboundController(RawMaterialInboundService rawMaterialInboundService) {
        this.rawMaterialInboundService = rawMaterialInboundService;
    }

    // ✅ 1. 입고 목록 조회
    @GetMapping("/list")
    public String getRawMaterialInbounds(Model model) {
        List<RawMaterialInbound> rawMaterialInbounds = rawMaterialInboundService.getAllRawMaterialInbounds();
        model.addAttribute("rawMaterialInbounds", rawMaterialInbounds);
        return "RawMaterialInbound"; // 📌 Thymeleaf 템플릿 (RawMaterialInbound.html)
    }

    // ✅ 2. 입고 등록 (폼 데이터 처리)
    @PostMapping("/register")
    public String createRawMaterialInbound(@ModelAttribute RawMaterialInbound rawMaterialInbound, Model model) {
        try {
            rawMaterialInboundService.createRawMaterialInbound(rawMaterialInbound);
            model.addAttribute("message", "등록 성공!");
        } catch (Exception e) {
            model.addAttribute("error", "등록 실패: " + e.getMessage());
        }
        return "redirect:/raw-material/inbound/list"; // 📌 등록 후 목록 페이지로 이동
    }

    // ✅ 3. 입고 수정 페이지 렌더링
    @GetMapping("/edit/{inboundId}")
    public String editInbound(@PathVariable Long inboundId, Model model) {
        RawMaterialInbound inbound = rawMaterialInboundService.getRawMaterialInboundById(inboundId);
        model.addAttribute("inbound", inbound);
        return "RawMaterialInboundEdit"; // 📌 수정 페이지 Thymeleaf 뷰
    }

    // ✅ 4. 입고 수정 처리
    @PostMapping("/update/{inboundId}")
    public String updateInbound(@PathVariable Long inboundId, @ModelAttribute RawMaterialInbound rawMaterialInbound, Model model) {
        try {
            rawMaterialInboundService.updateInbound(inboundId, rawMaterialInbound);
            model.addAttribute("message", "수정 성공!");
        } catch (Exception e) {
            model.addAttribute("error", "수정 실패: " + e.getMessage());
        }
        return "redirect:/raw-material/inbound/list"; // 📌 수정 후 목록 페이지로 이동
    }

    // ✅ 5. 입고 삭제 처리
    @GetMapping("/delete/{inboundId}")
    public String deleteRawMaterialInbound(@PathVariable Long inboundId, Model model) {
        try {
            rawMaterialInboundService.deleteRawMaterialInbound(inboundId);
            model.addAttribute("message", "삭제 완료!");
        } catch (Exception e) {
            model.addAttribute("error", "삭제 실패: " + e.getMessage());
        }
        return "redirect:/raw-material/inbound/list"; // 📌 삭제 후 목록 페이지로 이동
    }
}
