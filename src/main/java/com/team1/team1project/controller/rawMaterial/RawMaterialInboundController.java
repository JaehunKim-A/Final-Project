package com.team1.team1project.controller.rawMaterial;

import com.team1.team1project.dto.*;
import com.team1.team1project.service.rawMaterial.RawMaterialInboundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/raw-material/inbound")
@RequiredArgsConstructor
public class RawMaterialInboundController {

    private final RawMaterialInboundService rawMaterialInboundService;

    /** ✅ 1. 페이지 뷰 */
    @GetMapping("/list")
    public String getListPage() {
        return "RawMaterialInbound";
    }

    /** ✅ 2. 페이지 + 검색 API (POST + JSON) */
    @PostMapping("/api/list")
    @ResponseBody
    public ResponseEntity<PageResponseDTO<RawMaterialInboundDTO>> getPagedList(@RequestBody PageRequestDTO pageRequestDTO) {
        log.info("📦 입고 리스트 요청: {}", pageRequestDTO);
        PageResponseDTO<RawMaterialInboundDTO> response = rawMaterialInboundService.getPagedRawMaterialInbounds(pageRequestDTO);
        return ResponseEntity.ok(response);
    }

    /** ✅ 3. 입고 상세 조회 */
    @GetMapping("/api/{inboundId}")
    @ResponseBody
    public ResponseEntity<RawMaterialInboundDTO> getDetail(@PathVariable Long inboundId) {
        RawMaterialInboundDTO dto = rawMaterialInboundService.getRawMaterialInbound(inboundId);
        return ResponseEntity.ok(dto);
    }

    /** ✅ 4. 입고 등록 (POST JSON) */
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody RawMaterialInboundDTO dto) {
        rawMaterialInboundService.createRawMaterialInbound(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("입고 등록 완료");
    }

    /** ✅ 5. 입고 등록 (폼 방식) */
    @PostMapping("/register")
    public String registerForm(@ModelAttribute RawMaterialInboundDTO dto) {
        rawMaterialInboundService.createRawMaterialInbound(dto);
        return "redirect:/raw-material/inbound/list";
    }

    /** ✅ 6. 입고 수정 (PUT JSON) */
    @PutMapping("/api/update/{inboundId}")
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable Long inboundId, @RequestBody RawMaterialInboundDTO dto) {
        dto.setInboundId(inboundId);
        rawMaterialInboundService.modifyInboundStatus(inboundId, dto);
        return ResponseEntity.ok("입고 수정 완료");
    }

    /** ✅ 7. 상태만 수정 (PATCH JSON) */
    @PatchMapping("/api/status/{inboundId}")
    @ResponseBody
    public ResponseEntity<String> patchStatus(@PathVariable Long inboundId, @RequestBody Map<String, String> statusMap) {
        String status = statusMap.get("status");
        RawMaterialInboundDTO dto = new RawMaterialInboundDTO();
        dto.setStatus(status);
        rawMaterialInboundService.modifyInboundStatus(inboundId, dto);
        return ResponseEntity.ok("상태 수정 완료");
    }

    /** ✅ 8. 입고 삭제 (DELETE) */
    @DeleteMapping("/api/delete/{inboundId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long inboundId) {
        rawMaterialInboundService.deleteRawMaterialInbound(inboundId);
        return ResponseEntity.ok("삭제 완료");
    }

    /** ✅ 9. 히스토리 페이지 뷰 */
    @GetMapping("/history")
    public String getHistoryPage(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<RawMaterialInboundDTO> historyPage =
                rawMaterialInboundService.getRawMaterialInboundHistoryForTable("historyId", false, pageRequestDTO);

        model.addAttribute("historyPage", historyPage);
        return "raw-material/historyList";
    }

    /** ✅ 10. 히스토리 API (POST JSON + 정렬) */
    @PostMapping("/api/history")
    @ResponseBody
    public ResponseEntity<PageResponseDTO<RawMaterialInboundDTO>> getHistoryList(@RequestBody MachineHistoryRequestDTO request) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(request.getPage())
                .size(request.getSize())
                .type(request.getType())
                .keyword(request.getKeyword())
                .build();

        PageResponseDTO<RawMaterialInboundDTO> result =
                rawMaterialInboundService.getRawMaterialInboundHistoryForTable(
                        request.getSorter(), request.isAsc(), pageRequestDTO);

        return ResponseEntity.ok(result);
    }
}
