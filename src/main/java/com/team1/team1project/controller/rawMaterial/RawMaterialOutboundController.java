package com.team1.team1project.controller.rawMaterial;

import com.team1.team1project.dto.*;
import com.team1.team1project.service.finishedProduct.FinishedProductOutboundService;
import com.team1.team1project.service.rawMaterial.RawMaterialOutboundService;
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
@RequestMapping("/raw-material/outbound")
@RequiredArgsConstructor
public class RawMaterialOutboundController {

    private final RawMaterialOutboundService rawMaterialOutboundService;

    /** ✅ 1. 페이지 뷰 */
    @GetMapping("/list")
    public String getListPage() {
        return "RawMaterialOutbound";
    }

    /** ✅ 2. 페이지 + 검색 API (POST + JSON) */
    @PostMapping("/api/list")
    @ResponseBody
    public ResponseEntity<PageResponseDTO<RawMaterialOutboundDTO>> getPagedList(@RequestBody PageRequestDTO pageRequestDTO) {
        log.info("📦 입고 리스트 요청: {}", pageRequestDTO);
        PageResponseDTO<RawMaterialOutboundDTO> response = rawMaterialOutboundService.getPagedRawMaterialOutbounds(pageRequestDTO);
        return ResponseEntity.ok(response);
    }

    /** ✅ 3. 입고 상세 조회 */
    @GetMapping("/api/{outboundId}")
    @ResponseBody
    public ResponseEntity<RawMaterialOutboundDTO> getDetail(@PathVariable Long outboundId) {
        RawMaterialOutboundDTO dto = rawMaterialOutboundService.getRawMaterialOutbound(outboundId);
        return ResponseEntity.ok(dto);
    }

    /** ✅ 4. 입고 등록 (POST JSON) */
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody RawMaterialOutboundDTO dto) {
        rawMaterialOutboundService.createRawMaterialOutbound(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("입고 등록 완료");
    }

    /** ✅ 5. 입고 등록 (폼 방식) */
    @PostMapping("/register")
    public String registerForm(@ModelAttribute RawMaterialOutboundDTO dto) {
        rawMaterialOutboundService.createRawMaterialOutbound(dto);
        return "redirect:/raw-material/outbound/list";
    }

    /** ✅ 6. 입고 수정 (PUT JSON) */
    @PutMapping("/api/update/{outboundId}")
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable Long outboundId, @RequestBody RawMaterialOutboundDTO dto) {
        dto.setOutboundId(outboundId);
        rawMaterialOutboundService.modifyOutboundStatus(outboundId, dto);
        return ResponseEntity.ok("입고 수정 완료");
    }

    /** ✅ 7. 상태만 수정 (PATCH JSON) */
    @PatchMapping("/api/status/{outboundId}")
    @ResponseBody
    public ResponseEntity<String> patchStatus(@PathVariable Long outboundId, @RequestBody Map<String, String> statusMap) {
        String status = statusMap.get("status");
        RawMaterialOutboundDTO dto = new RawMaterialOutboundDTO();
        dto.setStatus(status);
        rawMaterialOutboundService.modifyOutboundStatus(outboundId, dto);
        return ResponseEntity.ok("상태 수정 완료");
    }

    /** ✅ 8. 입고 삭제 (DELETE) */
    @DeleteMapping("/api/delete/{outboundId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long outboundId) {
        rawMaterialOutboundService.deleteRawMaterialOutbound(outboundId);
        return ResponseEntity.ok("삭제 완료");
    }

    /** ✅ 9. 히스토리 페이지 뷰 */
    @GetMapping("/history")
    public String getHistoryPage(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<RawMaterialOutboundDTO> historyPage =
                rawMaterialOutboundService.getRawMaterialOutboundHistoryForTable("historyId", false, pageRequestDTO);

        model.addAttribute("historyPage", historyPage);
        return "rawMaterial/historyList";
    }

    /** ✅ 10. 히스토리 API (POST JSON + 정렬) */
    @PostMapping("/api/history")
    @ResponseBody
    public ResponseEntity<PageResponseDTO<RawMaterialOutboundDTO>> getHistoryList(@RequestBody MachineHistoryRequestDTO request) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(request.getPage())
                .size(request.getSize())
                .type(request.getType())
                .keyword(request.getKeyword())
                .build();

        PageResponseDTO<RawMaterialOutboundDTO> result =
                rawMaterialOutboundService.getRawMaterialOutboundHistoryForTable(
                        request.getSorter(), request.isAsc(), pageRequestDTO);

        return ResponseEntity.ok(result);
    }
}
