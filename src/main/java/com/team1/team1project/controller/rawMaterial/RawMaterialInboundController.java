package com.team1.team1project.controller.rawMaterial;

import com.team1.team1project.dto.RawMaterialInboundDTO;
import com.team1.team1project.service.rawMaterial.RawMaterialInboundService;
import org.springframework.http.ResponseEntity;
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

    // ✅ 1. 입고 목록 조회 + 페이지 렌더링
    @GetMapping("/list")
    public String getInboundList(Model model) {
        List<RawMaterialInboundDTO> list = rawMaterialInboundService.getAllRawMaterialInbounds();
        model.addAttribute("rawMaterialInbounds", list);
        return "RawMaterialInbound";
    }

    // ✅ 2. 입고 등록
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerInbound(@RequestBody RawMaterialInboundDTO dto) {
        rawMaterialInboundService.createRawMaterialInbound(dto);
        return ResponseEntity.ok("입고 등록 완료");
    }

    // ✅ 3. 입고 수정
    @PutMapping("/update/{inboundId}")
    @ResponseBody
    public ResponseEntity<String> updateInbound(@PathVariable Long inboundId,
                                                @RequestBody RawMaterialInboundDTO dto) {
        rawMaterialInboundService.updateInbound(inboundId, dto);
        return ResponseEntity.ok("입고 수정 완료");
    }

    // ✅ 4. 입고 삭제
    @DeleteMapping("/delete/{inboundId}")
    @ResponseBody
    public ResponseEntity<String> deleteInbound(@PathVariable Long inboundId) {
        rawMaterialInboundService.deleteRawMaterialInbound(inboundId);
        return ResponseEntity.ok("입고 삭제 완료");
    }
}
