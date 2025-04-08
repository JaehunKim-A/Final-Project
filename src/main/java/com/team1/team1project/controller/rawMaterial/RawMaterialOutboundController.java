package com.team1.team1project.controller.rawMaterial;

import com.team1.team1project.dto.RawMaterialOutboundDTO;
import com.team1.team1project.service.rawMaterial.RawMaterialOutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/raw-material/outbound")
public class RawMaterialOutboundController {

    private final RawMaterialOutboundService outboundService;

    // 1. 목록 조회 - HTML 렌더링용
    @GetMapping("/list")
    public String listPage(Model model) {
        List<RawMaterialOutboundDTO> list = outboundService.getAllOutbounds();
        model.addAttribute("outbounds", list);
        return "RawMaterialOutbound"; // Thymeleaf 뷰
    }

    // 2. 등록 (JSON)
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody RawMaterialOutboundDTO dto) {
        outboundService.registerOutbound(dto);
        return ResponseEntity.ok("등록 완료");
    }

    // 3. 삭제 (JSON)
    @DeleteMapping("/{outboundId}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long outboundId) {
        outboundService.deleteOutbound(outboundId);
        return ResponseEntity.ok("삭제 완료");
    }

    // 4. 상태 수정 (JSON)
    @PostMapping("/modify-status")
    @ResponseBody
    public ResponseEntity<?> modifyStatus(@RequestParam Long outboundId, @RequestParam String status) {
        outboundService.updateStatus(outboundId, status);
        return ResponseEntity.ok("상태 변경 완료");
    }

    // 5. 개별 조회 (선택 사항)
    @GetMapping("/{outboundId}")
    @ResponseBody
    public ResponseEntity<RawMaterialOutboundDTO> getOne(@PathVariable Long outboundId) {
        return ResponseEntity.ok(outboundService.getOutboundById(outboundId));
    }
}
